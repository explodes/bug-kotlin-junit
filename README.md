# Repository to replicate NoClassDefFoundError

I'm hoping this provides an easy way to interested parties in replicating a bug that is making my unit testing fail. 

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