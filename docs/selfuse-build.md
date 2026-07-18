# Self-use build

The `Sync upstream and build self-use APK` GitHub Actions workflow runs daily,
merges `Neamar/KISS`'s `master` branch, tests the result, and uploads an APK.
It can also be started manually from the repository's **Actions** tab.

Without signing secrets the workflow uploads a debug APK. Debug signing keys are
temporary on GitHub-hosted runners, so APKs from different runs might require an
uninstall before installation.

For APKs that update in place, create one keystore and add these repository
Actions secrets under **Settings → Secrets and variables → Actions**:

- `KISS_KEYSTORE_BASE64`: base64 encoding of the complete `.jks` file
- `KISS_STORE_PASSWORD`: keystore password
- `KISS_KEY_ALIAS`: key alias
- `KISS_KEY_PASSWORD`: key password

For example, create the keystore locally (the command asks for its password):

```shell
keytool -genkeypair -keystore kiss-selfuse.jks -storetype JKS \
  -alias kiss-selfuse -keyalg RSA -keysize 4096 -validity 10000
base64 -w0 kiss-selfuse.jks
```

Paste the second command's output into `KISS_KEYSTORE_BASE64`. Use
`kiss-selfuse` for `KISS_KEY_ALIAS`, and use the passwords entered in the first
command for the two password secrets.

Keep the original keystore and passwords backed up outside the repository. They
must never be committed. Losing the key means future APKs cannot update the
installed app. With all four secrets present, the workflow uploads a signed
release APK using the private application ID `fr.neamar.mykiss`.
