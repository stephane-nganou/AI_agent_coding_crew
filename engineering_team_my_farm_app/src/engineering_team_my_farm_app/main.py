#!/usr/bin/env python
import sys
import warnings
import os

from datetime import datetime

from engineering_team_my_farm_app.crew import EngineeringTeamMyFarmApp

warnings.filterwarnings("ignore", category=SyntaxWarning, module="pysbd")

# Create output directory if it doesn't exist
os.makedirs('output', exist_ok=True)

requirements = """
- Angular 20 for the frontend with standalone components
- a Rest API using the latest Spring boot version with java 21

Core Functionalities:
- Farm Overview: A section to tell the farm’s story (e.g., history, values, sustainability practices) with engaging visuals and text.
- Media Gallery: Display photos and embedded YouTube videos of the farm, its operations, and products.
- Product Catalog: A dynamic catalog showcasing farm products (e.g., organic produce, dairy, or value-added items like jams) with descriptions, prices, and availability.
- Product Reservations and Orders: Allow customers to reserve products (e.g., for pickup or delivery) and place orders with a secure checkout process.
- Social Media Integration: Links to and feeds from social media platforms (Facebook, TikTok, X, Instagram) for customer engagement and contact.
- Contact Form: A form for inquiries, with options to subscribe to newsletters or updates.
Other Farm-Related Features: Include typical farm website features (e.g., farm event calendar, blog for recipes or farming tips, or customer testimonials).
- allow Customers to register and login to place orders and buy products via Paypal (extendable with others payments methods)
- allow Admins to login in, add and edits products and catalog and others core functions of the application
- Automate processes (e.g., order notifications, inventory updates) to minimize ongoing involvement.
- authentication using jwt and oauth 2.0 to authenticate with google (extendable with facebook etc other...)
- Create an intuitive, visually appealing interface that encourages engagement and conversions. Mobile friendly
- be able to create sell reports and so in a defined time interval and export them as csv.
"""

def run():
    """
    Run the development crew.
    """
    inputs = {
        'requirements': requirements
    }
    
    try:
        EngineeringTeamMyFarmApp().crew().kickoff(inputs=inputs)
    except Exception as e:
        raise Exception(f"An error occurred while running the crew: {e}")


if __name__ == "__main__":
    run()
