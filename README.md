# PetVerse
PetVerse addresses the lack of centralized information and resources for street pet adoption by offering a comprehensive platform. It aims to solve the problem of stray pet overpopulation and abandonment by connecting users with nearby adoption centers and providing detailed profiles of street pets. Through a user-friendly interface, users can access real-time updates on available animals. Our research questions focus on understanding user needs, evaluating the effectiveness of the platform in facilitating adoptions, and measuring community engagement. By tackling this problem, we aim to reduce the number of stray pets on the streets and promote responsible pet ownership, ultimately improving animal welfare and fostering a stronger sense of community among pet lovers.

## Features

- Identify dog & cat breeds using images captured by the camera, pick image in gallery.
- Upload and interact with posts.
- Facilitate adoption processes.
- User profile management.

## Technology Stack

- **Android Studio**: Used for developing the application.
- **Kotlin**: Primary programming language for building the application.
- **Retrofit2**: Used for accessing Google Cloud Platform (GCP) APIs.
- **Jetpack Compose**: For building the application's user interface.

## Installation

- Android Studio [Download](https://developer.android.com/studio)
- JDK 8 or later [Download](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html)

### Steps

1. **Clone the repository:**

    ```sh
    git clone https://github.com/C241-PS068-PetVers/PetVerseApp.git
    ```

2. **Open the project in Android Studio:**

    - Launch Android Studio.
    - Select `Open an existing Android Studio project`.
    - Navigate to the cloned repository directory and select the `PetVerseApp` folder.

3. **Build the project:**

    - Android Studio will automatically start syncing the project. If it doesn't, you can sync manually by clicking `File > Sync Project with Gradle Files`.
    - Ensure that all dependencies are downloaded and the project builds successfully.

4. **Run the app:**

    - Connect an Android device via USB or start an emulator.
    - Click the `Run` button in Android Studio or select `Run > Run 'app'`.

## Project Structure

- `app/`: Contains the main application code.
- `gradle/`: Contains Gradle wrapper files.
- `gradlew`, `gradlew.bat`: Scripts for running Gradle commands.
- `build.gradle.kts`: Project-level build configuration.
- `settings.gradle.kts`: Settings for the Gradle build.
- `gradle.properties`: Gradle properties for the project.
- `.gitignore`: Specifies files to be ignored by Git.
