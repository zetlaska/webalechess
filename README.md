Java Web-Based Chess Game

A classic two-player chess game with a graphical user interface, built entirely in Java and playable in a web browser. This project demonstrates the use of Java for creating interactive, web-based applications.
Screenshots

A sample game in progress.
Features

    Full Chess Logic: Implements all standard chess rules, including piece movements, captures, castling, en passant, and pawn promotion.

    Interactive GUI: A user-friendly, clickable interface to play the game.

    Move Validation: The system prevents illegal moves from being made.

    Turn-Based Gameplay: Enforces alternating turns between White and Black.

    Check and Checkmate Detection: The game automatically detects and announces check and checkmate conditions.

    Game Reset: Start a new game at any time with the click of a button.

Technologies Used

    Core Logic: Java

    Frontend/GUI: Java Swing / AWT (or specify if you used JavaFX)

    Web Integration: Java Applets (or specify if using a different technology like Spring Boot with a web framework)

    Build Tool: Maven / Gradle (Specify which one, or if you are not using one)

Getting Started

Follow these instructions to get a copy of the project up and running on your local machine for development and testing purposes.
Prerequisites

    Java Development Kit (JDK) 8 or higher installed.

    Apache Maven (or your chosen build tool) installed.

    A modern web browser with Java plugin support (if using Applets).

Installation

    Clone the repository:

    git clone https://github.com/your-username/your-chess-repo.git

    Navigate to the project directory:

    cd your-chess-repo

    Build the project:
    If using Maven:

    mvn clean install

    If using Gradle:

    gradle build

    Run the application:
    If it's a runnable JAR:

    java -jar target/your-chess-app-1.0.jar

    If it's an Applet, open the accompanying HTML file (e.g., index.html) in a web browser.

How to Play

    Launch the application as described in the Installation section.

    The game board will appear with the pieces in their starting positions.

    White moves first. Click on the piece you want to move.

    The possible destination squares for the selected piece will be highlighted.

    Click on one of the highlighted squares to complete the move.

    The turn will then switch to the Black player.

    Continue playing until a checkmate or stalemate occurs.

    Click the "New Game" button at any time to reset the board and start over.
