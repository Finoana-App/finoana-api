package com.anonymous.finoanaapi.config;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import java.util.List;
import org.mockito.MockedStatic;

public class FirebaseConfig {
  private static MockedStatic<FirebaseAuth> mockFirebaseAuth;
  private static MockedStatic<FirebaseApp> mockFirebaseApp;
  private static FirebaseAuth mockFirebaseAuthInstance;
  private static List<FirebaseApp> mockFirebaseAppInstances;

  static void setup() {
    try {
      setupFirebaseApp();
      setupFirebaseAuth();
    } catch (FirebaseAuthException e) {
      throw new RuntimeException(e);
    }
  }

  private static void setupFirebaseAuth() throws FirebaseAuthException {
    mockFirebaseAuthInstance = mock();
    mockFirebaseAuth = mockStatic(FirebaseAuth.class);
    mockFirebaseAuth.when(FirebaseAuth::getInstance).thenReturn(mockFirebaseAuthInstance);
  }

  /**
   * Mock a chosen user for the firebase token filter This mock will last until the @AfterAll is
   * called, it will be erased when the new @BeforeAll is called
   */
  public static void setupFirebaseAuthUser(
      String idToken, String email, String name, String picture) throws FirebaseAuthException {
    var mockFirebaseToken = mock(FirebaseToken.class);
    // TODO: compare by using the contains, but doesn't work for some reasons
    // when(mockFirebaseAuthInstance.verifyIdToken(contains(idToken))).thenReturn(mockFirebaseToken);
    when(mockFirebaseAuthInstance.verifyIdToken(anyString())).thenReturn(mockFirebaseToken);
    when(mockFirebaseToken.getEmail()).thenReturn(email);
    when(mockFirebaseToken.getName()).thenReturn(name);
    when(mockFirebaseToken.getPicture()).thenReturn(picture);
  }

  private static void setupFirebaseApp() {
    mockFirebaseApp = mockStatic(FirebaseApp.class);
    mockFirebaseAppInstances = mock();
    mockFirebaseApp.when(FirebaseApp::getApps).thenReturn(mockFirebaseAppInstances);
    // Mock the app is registered
    // TODO: must be modified to mock all of the firebase setup
    when(mockFirebaseAppInstances.isEmpty()).thenReturn(false);
  }

  static void setDown() {
    mockFirebaseAuth.close();
    mockFirebaseApp.close();
  }
}
