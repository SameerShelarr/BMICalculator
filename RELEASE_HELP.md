# 🚀 How to Release a New Version

This project uses GitHub Actions to automate the creation of releases and the building of APKs.

## 1. Prerequisites
- Ensure all your changes are committed and pushed to the `master` branch.
- Ensure the version information in `app/build.gradle.kts` (versionCode and versionName) is updated if necessary.

## 2. Triggering the Release
Releases are triggered by pushing a **Git Tag** starting with the letter `v`.

### Using the Command Line (Recommended)
Run the following commands from your terminal:

```bash
# 1. Create a local tag (replace v1.0.0 with your actual version)
git tag v1.0.0

# 2. Push the tag to GitHub
git push origin v1.0.0
```

### Using GitHub Desktop
1. Go to the **History** tab.
2. Right-click your latest commit.
3. Select **Create Tag...** and enter your version (e.g., `v1.0.0`).
4. Click **Push origin** to send the tag to GitHub.

## 3. What happens next?
Once the tag is pushed:
1. **GitHub Actions** will automatically start the `Release Workflow`.
2. It will **Build the Release APK**.
3. It will **Create a new GitHub Release** using your tag name.
4. It will **Attach the APK** to the release.
5. It will **Generate Release Notes** based on your commit history.

## 4. Monitoring Progress
You can monitor the build progress by going to the **Actions** tab in your GitHub repository and selecting the "Release Workflow".

## 5. Branch Protection
This workflow is designed to work even with **Branch Protection** enabled on `master`, as it does not need to push any code changes back to the branch. It only creates a release based on the state of the code at the time the tag was created.

## 6. Tag Protection & Security
To prevent unauthorized releases, the following security measures are in place:
- **Tag Protection Rule:** A rule is configured for tags matching `v*`. Only repository admins (the owner) can create or push these tags.
- **Workflow Security Gate:** The Release Workflow includes an internal check to ensure it only executes if triggered by the repository owner (`SameerShelarr`) on the official repository. This prevents forks or unauthorized collaborators from triggering a release.

