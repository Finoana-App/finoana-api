# Finoana API 🤍

[![Java](https://img.shields.io/badge/Java-21.0.9-orange?logo=java)]()
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen?logo=springboot)]()
[![Gradle](https://img.shields.io/badge/Gradle-8.6.0-02303A?logo=gradle)]()
[![License](https://img.shields.io/badge/License-Apache-green)](LICENSE)
[![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-2.1-4baaaa.svg)](CODE_OF_CONDUCT.md)

Finoana is a Christian community platform designed to encourage **faith-centered sharing**, **prayer**, **mutual support**, and **spiritual growth**, all within a respectful and distraction-free environment.

> [!IMPORTANT]
> Finoana API is **under active development** and **not yet production-ready**.

## 🔧 Installation (Finoana API — Java Spring Boot)

Make sure to use atleast java **21** :

```bash
java --version
```

And :

```bash
git clone https://github.com/Finoana-App/finoana-api.git
cd finoana-api
```

### ⚙️ Configuration

- Copy the `.env.example` to `.env`
- Modify the content of the `.env`

### Run
// TODO: the following run script doesn't work
```bash
set -a
source .env
set +a
./gradlew bootRun
```

---

## 📌 Notes

* Java **21** is **required** because the API uses the Gradle toolchain defined at `JavaLanguageVersion.of(21)`.
* Using SDKMAN or jEnv is recommended to manage multiple Java versions on your system.
