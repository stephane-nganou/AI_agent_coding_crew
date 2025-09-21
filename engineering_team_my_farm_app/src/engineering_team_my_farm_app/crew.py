from crewai import Agent, Crew, Process, Task
from crewai.project import CrewBase, agent, crew, task
from crewai.agents.agent_builder.base_agent import BaseAgent
from crewai_tools import FileWriterTool, DirectoryReadTool
from typing import List
# If you want to run a snippet of code before or after the crew starts,
# you can use the @before_kickoff and @after_kickoff decorators
# https://docs.crewai.com/concepts/crews#example-crew-class-with-decorators


file_writer = FileWriterTool(root_dir=".")
dir_reader = DirectoryReadTool()

@CrewBase
class EngineeringTeamMyFarmApp():
    """EngineeringTeamMyFarmApp crew"""

    agents: List[BaseAgent]
    tasks: List[Task]

    @agent
    def engineering_lead(self) -> Agent:
        return Agent(
            config=self.agents_config['engineering_lead'],
            verbose=True,
            tools=[file_writer, dir_reader],
            #allow_code_execution=False,
            #code_execution_mode="safe",
            #max_execution_time=500, 
            #max_retry_limit=1
        )

    @agent
    def backend_dev_1(self) -> Agent:
        return Agent(
            config=self.agents_config['backend_dev_1'],
            verbose=True,
            tools=[file_writer, dir_reader],
            allow_code_execution=True,
            code_execution_mode="safe",
            max_execution_time=500, 
            max_retry_limit=3 
        )

    @agent
    def backend_dev_2(self) -> Agent:
        return Agent(
            config=self.agents_config['backend_dev_2'],
            verbose=True,
            tools=[file_writer, dir_reader],
            allow_code_execution=True,
            code_execution_mode="safe",
            max_execution_time=500, 
            max_retry_limit=3 
        )

    @agent
    def frontend_dev_1(self) -> Agent:
        return Agent(
            config=self.agents_config['frontend_dev_1'],
            verbose=True,
            tools=[file_writer, dir_reader],
            allow_code_execution=True,
            code_execution_mode="safe",
            max_execution_time=500, 
            max_retry_limit=3 
        )
    
    @agent
    def frontend_dev_2(self) -> Agent:
        return Agent(
            config=self.agents_config['frontend_dev_2'],
            verbose=True,
            tools=[file_writer, dir_reader],
            allow_code_execution=True,
            code_execution_mode="safe",
            max_execution_time=500, 
            max_retry_limit=3
        )

    @agent
    def frontend_tester(self) -> Agent:
        return Agent(
            config=self.agents_config['frontend_tester'],
            verbose=True,
            tools=[file_writer, dir_reader],
            allow_code_execution=True,
            code_execution_mode="safe",
            max_execution_time=500, 
            max_retry_limit=3 
        )

    @agent
    def backend_tester(self) -> Agent:
        return Agent(
            config=self.agents_config['backend_tester'],
            verbose=True,
            tools=[file_writer, dir_reader],
            allow_code_execution=True,
            code_execution_mode="safe",
            max_execution_time=500, 
            max_retry_limit=3 
        )

    @agent
    def devops_engineer(self) -> Agent:
        return Agent(
            config=self.agents_config['devops_engineer'],
            verbose=True,
            tools=[file_writer, dir_reader],
            allow_code_execution=True,
            code_execution_mode="safe",
            max_execution_time=500, 
            max_retry_limit=3 
        )


    @task
    def design_task(self) -> Task:
        return Task(
            config=self.tasks_config['design_task']
        )

    @task
    def backend_task_dev_1(self) -> Task:
        return Task(
            config=self.tasks_config['backend_task_dev_1']
        )

    @task
    def backend_task_dev_2(self) -> Task:
        return Task(
            config=self.tasks_config['backend_task_dev_2']
        )

    @task
    def frontend_task_dev_1(self) -> Task:
        return Task(
            config=self.tasks_config['frontend_task_dev_1']
        )

    @task
    def frontend_task_dev_2(self) -> Task:
        return Task(
            config=self.tasks_config['frontend_task_dev_2']
        )

    @task
    def backend_tests_task(self) -> Task:
        return Task(
            config=self.tasks_config['backend_tests_task']
        )

    @task
    def frontend_tests_task(self) -> Task:
        return Task(
            config=self.tasks_config['frontend_tests_task']
        )

    @task
    def devops_task(self) -> Task:
        return Task(
            config=self.tasks_config['devops_task']
        )



    @crew
    def crew(self) -> Crew:
        """Creates the EngineeringTeamMyFarmApp crew"""
        

        return Crew(
            agents=self.agents, # Automatically created by the @agent decorator
            tasks=self.tasks, # Automatically created by the @task decorator
            process=Process.sequential,
            verbose=True
        )
