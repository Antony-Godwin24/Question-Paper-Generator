import requests
import os

api_key = "AIzaSyBfXr3m88ntAkGL2PDK1a3MwM4tuCPfdNQ"
url = f"https://generativelanguage.googleapis.com/v1beta/models?key={api_key}"

try:
    response = requests.get(url)
    response.raise_for_status()
    models = response.json().get('models', [])
    print("Available Models:")
    for model in models:
        if 'generateContent' in model.get('supportedGenerationMethods', []):
            print(f"- {model['name']}")
except Exception as e:
    print(f"Error: {e}")
    if 'response' in locals():
        print(response.text)
