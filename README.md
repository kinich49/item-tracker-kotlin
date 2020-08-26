# Item tracker
Welcome to Item tracker! This my first attempt at creating a Kotlin app from scratch!

The intention of this project is to satisfy my need to keep track of my week-to-week shopping, such as groceries and stuff. This way I can visualise price increments, and be able to make better purchase decisions.

If you want to use this project for yourself, feel free to fork it. You can also fork the backend, which is located in another repo.

Feel free as well to report any bug or enhancement.

I hope you can forgive my lack of UX skills.

## Project setup
In local.properties, set a property called base_url that points to the web server. For example

```
base_url=http:localhost:9000/
```

This app is in development phase, so you need to also add a dummy username and password such as

```
username=root
password=hunter2
```

This will be enhanced in a future with a proper authentication system

## Build
Use the known gradle commnand
```
gradlew assembleDevelopment
```

## Features

### Offline First
This is an offline first app. The first time the app is run, it will try to download all the relevant data from the web server. Whenever the user is saving a new shopping list receipt, the app will persist the data in the local database first, then it will try to sync with remote web server. If there's no internet connection at that particular moment, a work will be scheduled to run whenever the device gains internet connection, regardless if app is running.


### Tecnologies

- Kotlin language
- Android Architecture Components
    - Data binding- WorkManager
    - ViewModel
    - Room
- Navigation Component
- MVVM Architecture


