# Scheduling Algorithms Simulation

## Table of Contents

1. [Introduction](#introduction)
2. [Algorithms Implemented](#algorithms-implemented)
3. [Project Structure](#project-structure)
4. [Classes and Methods](#classes-and-methods)
    - [Driver](#driver)
    - [Scheduling](#scheduling)
    - [FCFS](#fcfs)
    - [SJF](#sjf)
    - [SRTF](#srtf)
    - [RoundRobin](#roundrobin)
    - [PreemptivePriority](#preemptivepriority)
    - [NonPreemptivePriority](#nonpreemptivepriority)
    - [PCB](#pcb)
5. [Setup and Installation](#setup-and-installation)
6. [Usage](#usage)


---

## Introduction

This project is a simulation of various CPU scheduling algorithms implemented in Java. It demonstrates the application of Object-Oriented Programming (OOP) concepts such as inheritance, polymorphism, encapsulation, and abstraction. The project allows users to select a scheduling algorithm and observe its behavior through a Gantt chart and performance metrics like average waiting time and turnaround time.

---

## Algorithms Implemented

The following scheduling algorithms are implemented in this project:

1. **First Come First Serve (FCFS)**: Non-preemptive scheduling based on arrival time.
2. **Shortest Job First (SJF)**: Non-preemptive scheduling based on burst time.
3. **Shortest Remaining Time First (SRTF)**: Preemptive scheduling based on remaining burst time.
4. **Round Robin (RR)**: Preemptive scheduling with a fixed time quantum.
5. **Preemptive Priority Scheduling with Aging**: Preemptive scheduling based on priority, with aging to prevent starvation.
6. **Non-Preemptive Priority Scheduling with Aging**: Non-preemptive scheduling based on priority, with aging to prevent starvation.

---

## Project Structure

The project is organized into the following files:

- `Driver.java`: The main entry point of the application.
- `Scheduling.java`: Abstract base class for all scheduling algorithms.
- `FCFS.java`: Implementation of the First Come First Serve algorithm.
- `SJF.java`: Implementation of the Shortest Job First algorithm.
- `SRTF.java`: Implementation of the Shortest Remaining Time First algorithm.
- `RoundRobin.java`: Implementation of the Round Robin algorithm.
- `PreemptivePriority.java`: Implementation of the Preemptive Priority Scheduling algorithm.
- `NonPreemptivePriority.java`: Implementation of the Non-Preemptive Priority Scheduling algorithm.
- `PCB.java`: Represents a Process Control Block, encapsulating process details.

---

## Classes and Methods

### Driver

- **Purpose**: Provides a menu-driven interface for users to select and execute a scheduling algorithm.
- **Key Methods**:
  - `main(String[] args)`: Initializes processes and handles user input to execute the selected algorithm.
  - `showMenu()`: Displays the list of available scheduling algorithms.

### Scheduling

- **Purpose**: Abstract base class that defines the common structure and behavior for all scheduling algorithms.
- **Key Methods**:
  - `runAlgorithm()`: Simulates the execution of the scheduling algorithm and prints the Gantt chart.
  - `resetProperties(PCB process, int countWaiting)`: Abstract method to reset process properties.
  - `checkForArrival(Queue<PCB> readyQueue, PCB[] processes, int countArrival)`: Checks for newly arrived processes.
  - `checkForComeback(LinkedList<PCB> waitingQueue, Queue<PCB> readyQueue, int countWaiting)`: Handles processes returning from the waiting queue.

### FCFS

- **Purpose**: Implements the First Come First Serve scheduling algorithm.
- **Key Methods**:
  - `resetProperties(PCB process, int countWaiting)`: Resets process properties for FCFS.

### SJF

- **Purpose**: Implements the Shortest Job First scheduling algorithm.
- **Key Methods**:
  - `resetProperties(PCB process, int countWaiting)`: Resets process properties for SJF.

### SRTF

- **Purpose**: Implements the Shortest Remaining Time First scheduling algorithm.
- **Key Methods**:
  - `resetProperties(PCB process, int countWaiting)`: Resets process properties for SRTF.
  - `interruptProcess(int time)`: Handles process preemption based on remaining time.

### RoundRobin

- **Purpose**: Implements the Round Robin scheduling algorithm.
- **Key Methods**:
  - `resetProperties(PCB process, int countWaiting)`: Resets process properties for Round Robin.
  - `interruptProcess(int time)`: Handles process preemption based on the time quantum.

### PreemptivePriority

- **Purpose**: Implements the Preemptive Priority Scheduling algorithm with aging.
- **Key Methods**:
  - `resetProperties(PCB process, int countWaiting)`: Resets process properties for Preemptive Priority.
  - `decreasePriority(int time)`: Decreases the priority of processes in the ready queue.
  - `interruptProcess(int time)`: Handles process preemption based on priority.

### NonPreemptivePriority

- **Purpose**: Implements the Non-Preemptive Priority Scheduling algorithm with aging.
- **Key Methods**:
  - `resetProperties(PCB process, int countWaiting)`: Resets process properties for Non-Preemptive Priority.
  - `decreasePriority(int time)`: Decreases the priority of processes in the ready queue.

### PCB

- **Purpose**: Represents a Process Control Block, encapsulating process details such as ID, arrival time, burst time, priority, and more.
- **Key Methods**:
  - `clone()`: Creates a deep copy of the process.
  - `calculateAverageWaitingTime(PCB[] processes)`: Calculates the average waiting time for all processes.
  - `calculateAverageTurnaroundTime(PCB[] processes)`: Calculates the average turnaround time for all processes.

---

## Setup and Installation

1. **Prerequisites**:
   - Java Development Kit (JDK) 8 or higher.
   - IntelliJ IDEA or any Java-compatible IDE.

2. **Steps**:
   - Clone the repository to your local machine.
   - Open the project in your IDE.
   - Compile and run the `Driver.java` file.
   - Follow the on-screen menu to select and execute a scheduling algorithm.

## Usage

### Menu

The program provides a menu-driven interface for selecting a scheduling algorithm. The menu is displayed as follows:

![image](https://github.com/user-attachments/assets/61d3988a-b483-4ffe-8c50-e6ba235f3bb9)




### Example Input

The following table represents the process properties used for the example:

| Process ID | Arrival Time | Burst Time | Priority | IO Time |
|------------|--------------|------------|----------|---------|
| 1          | 0            | 10         | 2        | 3       |
| 2          | 1            | 8          | 4        | 2       |
| 3          | 3            | 14         | 6        | 3       |
| 4          | 4            | 7          | 8        | 1       |
| 5          | 6            | 5          | 3        | 0       |
| 6          | 7            | 4          | 6        | 1       |
| 7          | 8            | 6          | 9        | 2       |


**Note**: These values are currently hard-coded in Driver.java, they might be moved to a file later on.

### Example Output for Round Robin (Quantum = 5)

The Terminal-based Gantt chart and performance metrics for the Round Robin algorithm with a time quantum of 5 are as follows:

**Gantt Chart**:

![image](https://github.com/user-attachments/assets/11727de8-ee57-4dd2-8179-9dd63e206aaf)
