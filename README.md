"Find your ChatMate" App is an application that helps you to find a chatmate among your VK friends by chosen topic.
For this application user must have a VK account.
The application works on Google Cloud.

Link to the app:
34.136.208.204:8080

Starting point of the program:
https://github.com/feduuk/app/blob/d26a3012b03257f7ab728424794656858a967af0/web/src/main/java/com/web/HomeController.java#L11

SIDENOTE:
  Programm considers only 30 friends from given VK account because returning info about friends and their groups takes big ammount of time.
  Change max number of friends to consider can be done here:
  https://github.com/feduuk/app/blob/d26a3012b03257f7ab728424794656858a967af0/web/src/main/resources/application.properties#L9
