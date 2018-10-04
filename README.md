# CS431_Project
Completed in July 17, 2018, the project was to write a Java program that simulates a simplified version of the CPU scheduler, specifically showing the three algorithms: First-Come-First-Serve, Shortest-Job-First, and Round Robin (2 different time slice variations). Gantt charts and average completion time must be displayed for the algorithms with 3 given .txt files that list the jobs and and burst times (ms). All jobs are assumed to arrive at time 0.

The guidelines file was added to the project as "cs431_project_guidelines.txt" as reference of the detailed requirements.

Implementation is completely up to us as is how to display the information requested. I had chosen to use a GUI to display the charts and average completion time. With the implementation, I designed it so that it would be easy to switch between the three job files to populate the Gantt charts with the click of the "Calculate" button.

Gantt charts were also designed to be in sections with side-scrolling so that there is no cut off in the Gantt chart, allowing for the chart to be displayed in its entirety. The blue color was added to denote that the marked job had completed at that marked time interval. 

In order to keep the chart size minimized, all time slices in the chart were designed to have the same fixed width instead of adjusting the width to the length of time. With minimizing chart size, the hope was to keep the chart readable as the time duration gets longer. Also, the fixed width allows for the job text in the time slice to be readable (ex: not adjust to small width for time duration of 1 ms). 
