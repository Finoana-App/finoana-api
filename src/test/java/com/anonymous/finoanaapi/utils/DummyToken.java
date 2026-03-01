package com.anonymous.finoanaapi.utils;

public class DummyToken {
  public static String someToken() {
    return Math.random() * 10_000_000 + "";
  }
}
