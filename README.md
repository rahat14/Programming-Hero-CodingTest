# CodingTest
 A QUIZ App Featuring MVVM,Hilt,Navigation Component,Retrofit
#Project Summary
This application loads some quiz question from remote server and provide UI for quiz and calculate score also keep track of the HighScore. 
# Tech stack & Open-source libraries
- 100% [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Live Data](https://developer.android.com/topic/libraries/architecture/livedata) for asynchronous.
- JetPack🚀
  - ViewBinding - View binding is a feature that allows you to more easily write code that interacts with views.
  - LiveData With Flow - notify domain layer data to views.
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Hilt - To simplify Dagger-related infrastructure for Android apps.
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
- [Gson](https://github.com/google/gson) - Gson is a Java library that can be used to convert Java Objects into their JSON representation.
- [okhttp-logging-interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md) - logs HTTP request and response data.
- [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for coroutines,provides `runBlocking` coroutine builder used in tests
  for Android.

## Architecture
- MVVM Architecture (Model - View - ViewModel)
- Repository pattern

# Project Demo 
<img src="https://media.giphy.com/media/DRXvld6l1eqc9qFreu/giphy.gif" width="222" height="480" />
