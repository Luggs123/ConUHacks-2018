import json
from watson_developer_cloud import ToneAnalyzerV3
from watson_developer_cloud.tone_analyzer_v3 import ToneInput

credentials = json.load(open('res/credentials.json'))
username = credentials['tone_analyzer'][0]['credentials']['username']
password = credentials['tone_analyzer'][0]['credentials']['password']


tone_analyzer = ToneAnalyzerV3(
	version='2017-09-21',
	username=username,
	password=password,
)

def analyze(text):
	output = tone_analyzer.tone(text, content_type='text/plain')
	print(json.dumps(output))


if __name__ == '__main__':

	text = input("Enter text to input: ")
	analyze(text)