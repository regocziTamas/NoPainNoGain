# NoPainNoGain

### About No Pain No Gain
I created this app to be my pet-project/reference project during my studies at Codecool. Codecool is a programming bootcamp located in Budapest.

The purpose of this app is to help users manage their time they spend in the gym, so that they do not waste it by talking to others, staring out the window, or by browsing their phones (except for this app, of course). No Pain No Gain always keeps the user up-to-date as to what exercise they should be doing at the moment, and once they are done, it measures the rest time which has been assigned for that particular set.

Using the app's Workout Editor, users are able to assemble their own workouts from the exercises that come preloaded with the app. The Editor is capable of modelling all kinds of gym workouts, ranging from the simplest routines to more complex ones containing super- and tri-sets with varying reps and rest intervals.

The app also has a Workout Store feature (though everything is free), where users can find workouts that were downloaded from a central server and were created by me. Everytime the app starts, it looks for changes in the central database on the server that were applied since the last run of the application. This way, the app's database is always synced with the central one. The exercises are synced in the same manner. 

### Technical Details
The app is written in native Java for Android. I chose this language (instead of a cross-platform mobile programming language), because I wanted to pick up something new that is not taught in Codecool, but also I wanted to maintain and deepen my existing Java knowledge. 

For persisting data, the app uses two SQLite databases, one for the local workouts, and one for the exercises and workouts which are synced with the backend server. On the devices, the databases are handled with Google's Room Persistence Library. In order to pass the Workout model classes back and forth between the activities, they are serialized and deserialized with Jackson.

The backend server is a Spring Boot application that persists the data in a PostgreSQL database. 

### Links
https://github.com/regocziTamas/NoPainNoGainBackend - The backend server for the application
https://github.com/regocziTamas/NoPainNoGainAdmin - The Angular front end for the server above
