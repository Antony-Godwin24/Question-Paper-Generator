# Question Paper Generator

A Spring Boot application that leverages Google's Gemini AI to automatically generate question papers based on user requirements. It supports PDF generation and user authentication.

## Features

-   **AI-Powered Generation**: Uses Google Gemini 2.5 Flash model to generate relevant questions.
-   **PDF Export**: Generates downloadable PDF question papers using iTextPDF.
-   **User Authentication**: Secure login and registration using JWT (JSON Web Tokens).
-   **Database Integration**: Stores user data and generated papers in MongoDB.
-   **Responsive UI**: Simple web interface for interacting with the generator.

## Tech Stack

-   **Backend**: Java 17, Spring Boot 3.2.3
-   **Database**: MongoDB
-   **AI Model**: Google Gemini 2.5 Flash
-   **Security**: Spring Security, JWT
-   **PDF Generation**: iTextPDF
-   **Frontend**: HTML, CSS, JavaScript

## Prerequisites

-   Java 17 or higher
-   Maven 3.6+
-   MongoDB (locally installed or via Docker)

## Setup & Installation

1.  **Clone the repository**
    ```bash
    git clone https://github.com/Antony-Godwin24/Question-Paper-Generator.git
    cd Question-Paper-Generator
    ```

2.  **Configure Database**
    -   Ensure MongoDB is running locally on the default port `27017`.
    -   The application will automatically create the necessary database (`question-paper-generator`) and collections.

3.  **Environment Variables**
    -   This project uses a `.env` file to manage secrets.
    -   Copy the example file to create your local configuration:
        ```bash
        cp .env.example .env
        ```
    -   Open `.env` and fill in your actual credentials:
        ```properties
        JWT_SECRET=your_secure_jwt_secret_at_least_512_bits
        GEMINI_API_KEY=your_google_gemini_api_key
        ```
    -   **Security Note**: Never commit your `.env` file to version control. It is already added to `.gitignore`.

4.  **Run the Application**
    -   The project is configured with `spring-dotenv`, so it will automatically load variables from your `.env` file.
    -   Simply run:
        ```bash
        mvn spring-boot:run
        ```

## Usage

1.  Start the application.
2.  Open your browser and navigate to `http://localhost:8081`.
3.  Register/Login to access the generator.
4.  Input the subject, difficulty, and other parameters to generate a question paper.