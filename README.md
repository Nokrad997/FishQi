# FishQi - Language Flashcards Application
FishQi is a language flashcards application designed to help users learn and improve their language skills. It features a backend server built with Spring Boot, a frontend application developed with React and TypeScript, an H2 database for data storage, and a Dockerized FTP server for file management.

The application exclusively utilizes modals for user interactions, avoiding redirection to subpages. Each visible button serves the purpose of displaying a modal for a specific action. The user experience remains centered on the main page, with the only exception occurring during the logout process when the application requires reloading.

Additionally, the application employs JWT-based authentication for user authorization and interacts with a RESTful API for seamless communication between the frontend and backend. Furthermore, it is equipped with Swagger for easy API documentation and testing.

## App overview
### Navbar:
At the top of the application there is a navbar with sets of buttons and application name.


#### Login and Registration Buttons: 
Initially, the navbar contains buttons for logging in and registering.
![Zrzut ekranu 2024-05-25 o 15 01 39](https://github.com/Nokrad997/FishQi/assets/115646961/01a93923-4b04-43df-aa09-e7f5a674c9a0)

##### Logged-in User View:
After logging in, the login and registration buttons are replaced with buttons for logout, account management, and creating sets.
![Zrzut ekranu 2024-05-25 o 15 02 08](https://github.com/Nokrad997/FishQi/assets/115646961/54322d39-c3b0-44a1-8d2d-81933859d259)

##### Admin Access:
If the user logging in is an admin, an additional button for accessing the admin page is displayed, allowing the admin to manage users data.
![Zrzut ekranu 2024-05-25 o 15 02 30](https://github.com/Nokrad997/FishQi/assets/115646961/5a2c8065-4ab2-4014-8c9c-1ba89206a00e)

#### Search Button: 
A button for searching sets.
### Main Panel:

Below the navbar, the main panel displays sets of flashcards.
The panel consists of four tabs:
##### Most Popular: Displays sets that are most frequently rated by users.
![Zrzut ekranu 2024-05-25 o 15 09 46](https://github.com/Nokrad997/FishQi/assets/115646961/ff6bff26-9bbe-40ae-97b5-38455fec37bd)

##### Highest Rating: Shows sets with the highest user ratings.
![Zrzut ekranu 2024-05-25 o 15 10 04](https://github.com/Nokrad997/FishQi/assets/115646961/0efe7804-eb54-47c9-8ec6-cd98cfc1f2a5)

##### My Sets: Allows users to view and edit their own sets.
![Zrzut ekranu 2024-05-25 o 15 10 17](https://github.com/Nokrad997/FishQi/assets/115646961/02fa40bb-c38f-4303-9c26-28ff95e05cdb)

##### My Starred: Provides access to sets that the user has rated.
![Zrzut ekranu 2024-05-25 o 15 10 30](https://github.com/Nokrad997/FishQi/assets/115646961/4105f6e7-ef55-4757-bb37-7f20e97bdb1e)

#### Each tab displays sets in the form of:
##### Image:Which is choosen by author of the set
##### Title:The title of the set.
##### Author Information: Details about the author of the set.
##### Description:Brief information about the set.
##### Rating:The overall rating of the set.
##### Display Button: Button for viewing the set in detail.

### Modals
#### Account
The Account modal serves as a user profile management interface, allowing users to view and update their account information. Here's a breakdown of its components:
##### User Information Display:
Upon opening, the modal loads the user's information into corresponding input fields, such as email and username.
##### Change Password Section:
This section includes an input field for entering a new password. Initially, the field remains empty. However, as the user starts typing a new password, a second input field appears, prompting the user to confirm the new password.
##### Save and logout Button:
At the bottom of the modal, there's a button labeled "Save and logout." Clicking this button saves any modifications made to the user's account information, including the updated email, username, and password (if changed) and reloads the page. 
![Zrzut ekranu 2024-05-25 o 15 14 11](https://github.com/Nokrad997/FishQi/assets/115646961/04e61fc1-f2a1-47d7-aa58-013043aed7bb)

#### Create/edit set
The Create/Edit Set modal provides users with a comprehensive interface for creating or editing language flashcard sets. Here's an overview of its features:
##### Title Input: Users can input the title of the flashcard set.
##### Language Input: This field allows users to specify the language of the flashcard set.
##### Accessibility Modifier: Users can select a modifier to control the accessibility of the set, determining whether it's visible to the public or restricted to private use.
##### Description Input: Provides a space for users to enter a description of the flashcard set.
##### Default Image Selection: Users can upload or select a default image for the flashcard set.
##### Word and Translation Input Pair: Initially, there's a single pair of input fields for entering a word and its translation. Clicking the plus button adds another pair of input fields, allowing users to add multiple word-translation pairs to the set.
##### Save Button: At the bottom of the modal, there's a button labeled "Save." Clicking this button saves the created or edited flashcard set, including all the specified details and word-translation pairs.
![Zrzut ekranu 2024-05-25 o 15 14 38](https://github.com/Nokrad997/FishQi/assets/115646961/1a5a26fa-3af8-4c21-9ef3-ec7463a810ba)

#### Admin page
The Admin Modal provides administrators with a comprehensive interface for managing user accounts within the application. Here's a breakdown of its features:
##### User Information Rows: Each row represents a user account within the application and displays essential user information such as email, username, and role in the system (user/admin).
##### Editable Fields: Administrators can edit each piece of user information directly within the modal. This includes modifying the user's email, username, and role in the system.
##### Save Button: Each row features a gray "Save" button. When any information is edited, the Save button associated with that row changes its color to green, indicating that changes have been made. Clicking the Save button commits the changes to the user's account.
##### Delete Button: In addition to the Save button, there's a red "Delete" button in each row. Clicking this button removes the user's account from the system permanently, along with all associated flashcard sets.
![Zrzut ekranu 2024-05-25 o 15 15 11](https://github.com/Nokrad997/FishQi/assets/115646961/51dc8061-def9-42be-9a02-8f71ac2b858d)

#### Search
The Search Modal provides users with a convenient way to search for specific flashcard sets within the application. Here's how it works:
##### Search Input: Users can enter their search query into a text input field.
##### Search Options Dropdown: Beneath the search input, there's a dropdown menu that allows users to select the search criteria. The available options include "Title," "Author," and "Language." By default, the search is set to "Title."
#### Search Results: As users type their search query into the input field, the modal dynamically displays search results below. Each row in the search results displays the following information:
##### Title of the Flashcard Set
##### Author
##### Language
##### Description
##### View Set Button
![Zrzut ekranu 2024-05-25 o 15 16 18](https://github.com/Nokrad997/FishQi/assets/115646961/bf543fc4-1d41-47ad-a9c9-1b6a28c86645)

### View set
The View Set modal provides users with an interactive interface to review flashcard sets. Here's how it's structured:
##### Main Display Area: In the center of the modal, there's a gray field where the words of the flashcard set are displayed. Clicking on this gray field toggles the display between the word and its translation.
##### Navigation Arrows: On the left and right sides of the main display area, there are arrows that allow users to navigate through the flashcard set, moving to the previous or next word pair.
##### Rating System: At the bottom of the modal, there are star icons that users can interact with to rate the flashcard set. If the user has already rated the set, the corresponding number of stars will be highlighted in yellow. If the user has not yet rated the set, all stars will appear gray.
![Zrzut ekranu 2024-05-25 o 15 17 02](https://github.com/Nokrad997/FishQi/assets/115646961/f0a7e7fc-2fb4-453c-be07-c899f41d6bf8)


## Technologies Used
### Backend:
#### Spring Boot: 
A powerful framework for building Java-based backend applications.
#### H2 Database: 
A lightweight and fast in-memory database for storing flashcard data.
#### Docker: 
Containerization technology used to deploy the backend server.
### Frontend:
#### React: 
A JavaScript library for building user interfaces.
#### TypeScript: 
A statically typed superset of JavaScript, adding optional static typing.
#### Docker:
Containerization technology used to deploy the frontend application.
### Infrastructure:

#### Docker:
Used for containerization of the backend server, frontend application and FTP server.

## Installation and Deployment using Docker Compose
To install and deploy the FishQi application using Docker Compose, follow these steps:

### Clone the Repository:
```
git clone https://github.com/Nokrad997/FishQi.git
```
### Install Docker and Docker Compose:

Make sure Docker and Docker Compose are installed on your system. If not, follow the official installation instructions:
#### [Install Docker](https://docs.docker.com/engine/install/)
#### [Install Docker Compose](https://docs.docker.com/compose/install/)
### Build and Run the Application:

### Navigate to the root directory of the FishQi application.

Run the following command to build and start the application containers:
```
docker-compose up --build
```
### Accessing the Application:
Once all containers are up and running, you can access the FishQi application through your web browser.
The application should be available at http://localhost:5173.
