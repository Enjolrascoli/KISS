# Signed release builds

The `Sync upstream and build signed APK` workflow runs daily, merges
`Neamar/KISS`'s `master` branch, tests the result, and uploads
`KISS-release.apk`. It can also be started manually from the repository's
**Actions** tab.

Upstream changes can include GitHub Actions workflow files. The default
`GITHUB_TOKEN` cannot push those changes, so the checkout uses the repository
deploy key stored in the `UPSTREAM_SYNC_SSH_KEY` Actions secret. Its public key
must be registered under **Settings → Deploy keys** with write access enabled.
This key only grants access to this fork. APK artifacts are uploaded before
pushing, so a push failure does not prevent downloading a successful build.

Release signing is mandatory. Add these repository Actions secrets under
**Settings → Secrets and variables → Actions**:

- `KISS_RELEASE_KEYSTORE_BASE64`: base64 encoding of the complete keystore
- `KISS_RELEASE_STORE_PASSWORD`: keystore password
- `KISS_RELEASE_KEY_ALIAS`: key alias
- `KISS_RELEASE_KEY_PASSWORD`: key password

For the existing local keystore, obtain the first secret with:

```shell
base64 -w0 /home/enjolrascoli/Documents/repo/kiss.jks
```

Inspect its aliases after entering the keystore password with:

```shell
keytool -list -keystore /home/enjolrascoli/Documents/repo/kiss.jks
```

Keep the original keystore and passwords backed up outside the repository.
They must never be committed. Losing the key means future APKs cannot update
the installed app. The signed release uses the private application ID
`fr.neamar.mykiss` so it can coexist with the official KISS release.
