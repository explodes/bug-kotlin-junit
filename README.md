# Repository to replicate NoClassDefFoundError

# Fixed!

The solution was to add

```groovy
    task copyTestClasses(type: Copy) {
        from "build/tmp/kotlin-classes/devDebugUnitTest"
        into "build/intermediates/classes/dev/debug"
    }

    task copySdkClasses(type: Copy) {
        from "build/tmp/kotlin-classes/devDebug"
        into "build/intermediates/classes/dev/debug"
    }
```

to the module's `build.gradle` and run these tasks after `:api:assembleDevDebug`

This is a change to the previous workaround which included the following tasks:

```groovy
    task copyTestClasses(type: Copy) {
        from "build/tmp/kotlin-classes/devDebugUnitTest"
        into "build/intermediates/classes/devDebug" // subtle change here
    }

    task copySdkClasses(type: Copy) {
        from "build/tmp/kotlin-classes/devDebug"
        into "build/intermediates/classes/devDebug" // and a subtle change here
    }
```

# Preface

I'm hoping this provides an easy way to interested parties in replicating a bug that is making my unit testing fail. 

![Screenshot](https://i.imgur.com/aF19YJx.png)

`com.explod.api.UserCreated` is a class defined in `UserDto.kt` as:

```kotlin
data class UserCreated(
        val username: String,
        val email: String,
        val created: Date
)
```

Regardless of the Kotlin data class being initialized in the test code, the same error occurs.

## Usage

Use the `Replicate NoClassDefFoundError` run configuration to see the bug.

## The situation

The Android project consists of 1 application module (`app`) and 4 library modules (`api`, `database`, `logging`, `repo`).

The `api` module is the only module with significant content. When unit tests were added, the bug presented itself.
  
Running the `Bug` test class reliably reproduces the error.
 
## My findings
 
As a new user of Kotlin, my knowledge is limited. However, I believe the issue is isolated only in unit testing, but I'm not an expert here.

[Reddit: Android Studio 3.0 Canary 2 is now available](https://www.reddit.com/r/androiddev/comments/6dkogi/android_studio_30_canary_2_is_now_available/)

["Looks like junit tests in kotlin are still broken." - H3x0n](https://www.reddit.com/r/androiddev/comments/6dkogi/android_studio_30_canary_2_is_now_available/di3uzpi/)