# Intelligent Document Management System (IDMS)

A JavaFX-based desktop application developed as a **Data Structures Semester Project** at **COMSATS University Islamabad, Abbottabad Campus**.

The Intelligent Document Management System (IDMS) is designed to provide efficient document handling features through the practical implementation of **Data Structures and Algorithms**.

The current system provides PDF document reading, text extraction, custom Huffman-based file compression, decompression, file processing, and a modern JavaFX graphical user interface.

---

# Project Overview

IDMS is a desktop application that focuses on intelligent document management and processing.

The project demonstrates how theoretical concepts from the Data Structures course can be applied to solve real-world problems.

The current implementation includes:

- PDF document reading
- PDF text extraction
- Custom Huffman file compression
- File decompression
- Compression statistics
- File saving functionality
- Drag and drop file selection
- Stack-based navigation management
- Modern JavaFX user interface

---

# Features

## PDF Reader Module

The PDF Reader module allows users to open and analyze PDF documents.

Features:

- Open PDF files
- Render PDF pages visually
- Extract text from PDF documents
- Switch between Image View and Text View
- Navigate through PDF pages
- Display document information


## Huffman Compression Module

IDMS implements a custom **Huffman Coding algorithm** for file compression.

Features:

- Byte frequency calculation
- Huffman tree construction
- Binary code generation
- File compression
- File decompression
- Compression ratio calculation
- File size comparison


## File Management

Features:

- File selection using JavaFX FileChooser
- Drag and drop file support
- Save processed files
- Display processing status


## Navigation System

A custom scene navigation system is implemented using stacks.

Features:

- Scene history tracking
- Back navigation
- Application state management

---

# Data Structures and Algorithms Used

| Feature | Concept Used |
|---|---|
| Huffman Compression | Greedy Algorithm |
| Huffman Tree | Binary Tree |
| Minimum Frequency Selection | Priority Queue |
| Navigation History | Stack |
| File Processing | Java I/O Streams |
| PDF Processing | Apache PDFBox |

---

# Technology Stack

## Programming Language

- Java 21


## GUI Framework

- JavaFX 21


## Build Tool

- Apache Maven


## Libraries

- Apache PDFBox 3.0.5


## Concepts

- Object-Oriented Programming
- Data Structures
- Algorithms
- Event Driven Programming
- File Handling

---

# Project Architecture

```
                 MainFrame

                    |
     --------------------------------
     |              |               |
SelectFileGUI    Reader       SceneHistory

                    |
            HuffmanCompressor

                    |
              HuffmanNode

```

---

# Project Structure

```
IDMS
│
├── pom.xml
├── README.md
│
├── docs
│   │
│   ├── uml
│   │   ├── class-diagram.png
│   │   ├── architecture-diagram.png
│   │   ├── compression-sequence-diagram.png
│   │   └── reader-sequence-diagram.png
│   │
│   └── screenshots
│       ├── home.png
│       ├── compression.png
│       └── reader.png
│
└── src
    │
    └── main
        │
        ├── java
        │   └── ds.comsats.idms.idms
        │       ├── MainFrame.java
        │       ├── SelectFileGUI.java
        │       ├── Reader.java
        │       ├── SceneHistory.java
        │       ├── HuffmanCompressor.java
        │       └── HuffmanNode.java
        │
        └── resources
            └── images

```

---

# UML Documentation

## Class Diagram

![Class Diagram](docs/uml/class-diagram.png)


## System Architecture

![Architecture Diagram](docs/uml/architecture-diagram.png)


## Compression Workflow

![Compression Sequence](docs/uml/compression-sequence-diagram.png)


## PDF Reader Workflow

![Reader Sequence](docs/uml/reader-sequence-diagram.png)

---

# Application Screenshots

## Home Screen

![Main Frame](https://raw.githubusercontent.com/Jaffer-29/Docuflow-IDMS/61366af1660c36ddeddf2858278a8021f0d734f9/docs/Screenshorts/Main%20Frame.png)


## Compression Module

![Compression File](https://raw.githubusercontent.com/Jaffer-29/Docuflow-IDMS/15cc4196f8e498d74a06c7161dde8db61576cab8/docs/Screenshorts/Compression%20File.png)


## PDF Reader Module

![Reader Frame](https://raw.githubusercontent.com/Jaffer-29/Docuflow-IDMS/15cc4196f8e498d74a06c7161dde8db61576cab8/docs/Screenshorts/Reader%20Frame.png)

---

# Installation and Running

## Requirements

Make sure the following are installed:

- JDK 21 or higher
- Apache Maven
- JavaFX dependencies


## Clone Repository

```bash
git clone YOUR_REPOSITORY_URL
```


## Navigate To Project

```bash
cd IDMS
```


## Run Application

```bash
mvn clean javafx:run
```

---

# Application Workflow

## File Compression Workflow

```
Select File

     ↓

Read File Bytes

     ↓

Calculate Byte Frequency

     ↓

Build Huffman Tree

     ↓

Generate Binary Codes

     ↓

Create Compressed File

```


## PDF Reading Workflow

```
Select PDF File

       ↓

Apache PDFBox Processing

       ↓

Render Pages / Extract Text

       ↓

Display Through JavaFX Interface

```

---

# Current Implementation Status

Implemented:

✅ JavaFX Desktop Interface  
✅ PDF Reader  
✅ PDF Text Extraction  
✅ PDF Page Navigation  
✅ Huffman Compression Algorithm  
✅ Huffman Decompression Algorithm  
✅ Compression Statistics  
✅ File Saving System  
✅ Drag and Drop File Selection  
✅ Stack-Based Scene Navigation  

---

# Future Enhancements

The following features are planned according to the original project proposal:

## Intelligent Search System

- HashMap-based inverted index
- Fast document searching
- KMP string matching algorithm


## Auto Complete System

- Trie-based word suggestions


## Document Management

- Recent files tracking using Doubly Linked List
- Frequently opened files using Priority Queue


## Document Analytics

- Word frequency analysis
- Character statistics
- Reading time estimation
- Compression reports


## Additional Features

- Duplicate file detection using hashing
- Automatic document classification
- Database persistence
- Support for additional document formats

---

# Limitations

- Currently focused on PDF document processing
- Search indexing module is not implemented yet
- No permanent database storage
- Limited document format support

---

# Academic Information

**Data Structures Semester Project**  
Spring 2026

**COMSATS University Islamabad**  
**Abbottabad Campus**

---

# Team Members

**Muhammad Jaffer**  
**Saif ul Hassan**

---

# License

This project was developed for academic purposes as part of the Data Structures course.
