# Deploying Application on Heroku
This guide will walk you through deploying a Spring Boot application built with Java 17 and Gradle to Heroku.

## Prerequisites

Before you begin, make sure you have the following tools installed:

1. **Heroku Account**: If you don’t already have one, sign up [here](https://signup.heroku.com/).
2. **Heroku CLI**: Install the Heroku CLI from [Heroku CLI Installation](https://devcenter.heroku.com/articles/heroku-cli).
3. **Java 17**: Install JDK 17 from [AdoptOpenJDK](https://adoptopenjdk.net/).
4. **Gradle**: Install Gradle from [Gradle Installation](https://gradle.org/install/).
5. **Git**: Install Git from [Git Download](https://git-scm.com/).


## Step 1: Add the `Procfile`
Heroku uses a `Procfile` to determine how to run your application.

1. In the root directory of your Spring Boot project, create a file named `Procfile` (no extension).
2. Add the following content to the `Procfile`:

   ```txt
   web: java -jar build/libs/your-application-name.jar

## Step 2: Add `system.properties`
To ensure Heroku uses Java 17 to run your application, create a file named system.properties in the root of your project.
1. Add the following content to system.properties

   ```txt
   java.runtime.version=17

## Step 3: Create a Heroku Application
1. Log in to Heroku using the Heroku CLI:
   ```txt
    heroku login
2. Create a new Heroku application:
   ```txt
    heroku create your-app-name 
  Replace your-app-name with the name you want for your Heroku app. If you leave it blank, Heroku will generate a name.

## Step 4: Add Heroku Remote
1. After creating your app, Heroku will provide a remote Git repository URL. Add it as a remote to your local Git repository:
   
   ```txt
   git remote add heroku https://git.heroku.com/your-app-name.git 
Replace your-app-name with your Heroku app name.

## Step 5: Deploy to Heroku

1. Deploy your app to Heroku by pushing the code to the Heroku remote repository:
   ```txt
       git push heroku master
2. Heroku will automatically detect the Spring Boot application and use the Procfile and system.properties to build and run it. 

## Step 6: Open Your Application

1. Once the deployment is complete, open your application in the browser using:
   ```txt
       heroku open --app app-name

This will open your newly deployed application on Heroku in your default web browser.

## Step 7: View Logs
 You can view your app's logs in real-time using the Heroku CLI:
1. To view logs, run the following command:
   ```txt
       heroku logs --tail --app app-name
   
This will stream your application logs, allowing you to monitor its output.