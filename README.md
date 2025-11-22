# Question Paper Generator

A Spring Boot application that leverages Google's Gemini AI to automatically generate question papers based on user requirements. It supports PDF generation and user authentication.

## Features

-   **AI-Powered Generation**: Uses Google Gemini 2.5 Flash model to generate relevant questions.
-   **PDF Export**: Generates downloadable PDF question papers using iTextPDF.
-   **User Authentication**: Secure login and registration using JWT (JSON Web Tokens).
-   **Database Integration**: Stores user data and generated papers in MySQL.
-   **Responsive UI**: Simple web interface for interacting with the generator.

## Tech Stack

-   **Backend**: Java 17, Spring Boot 3.2.3
-   **Database**: MySQL 8
-   **AI Model**: Google Gemini 2.5 Flash
-   **Security**: Spring Security, JWT
-   **PDF Generation**: iTextPDF
-   **Frontend**: HTML, CSS, JavaScript

## Prerequisites

-   Java 17 or higher
-   Maven 3.6+
-   MySQL Server

## Setup & Installation

1.  **Clone the repository**
    ```bash
    git clone https://github.com/Antony-Godwin24/Question-Paper-Generator.git
    cd Question-Paper-Generator
    ```

2.  **Configure Database**
    -   Create a MySQL database named `QnPaper`.
    -   The application is configured to create tables automatically (`ddl-auto=update`).

3.  **Environment Variables**
    -   This project uses a `.env` file to manage secrets.
    -   Copy the example file to create your local configuration:
        ```bash
        cp .env.example .env
        ```
    -   Open `.env` and fill in your actual credentials:
        ```properties
        DB_PASSWORD=your_mysql_password
        JWT_SECRET=your_secure_jwt_secret
        GEMINI_API_KEY=your_google_gemini_api_key
        ```

4.  **Run the Application**
    -   **Using IDE (IntelliJ/Eclipse)**:
        -   Make sure to install a plugin like **EnvFile** to load the `.env` file into your run configuration.
        -   Or manually set the environment variables in your IDE's Run Configuration.
    -   **Using Command Line**:
        -   You may need to export the variables first or pass them inline.
        ```bash
        mvn spring-boot:run
        ```

## Usage

1.  Start the application.
2.  Open your browser and navigate to `http://localhost:8080`.
3.  Register/Login to access the generator.
4.  Input the subject, difficulty, and other parameters to generate a question paper.

## Security Note

-   **Never commit your `.env` file.** It is included in `.gitignore` to prevent accidental leakage of API keys and passwords.
-   If you suspect your keys are compromised, revoke them immediately and generate new ones.
