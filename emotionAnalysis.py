import json

from watson_developer_cloud import ToneAnalyzerV3

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
	print(json.dumps(output, indent=2))


if __name__ == '__main__':
	filepath = 'res/file.txt'
	with open(filepath) as text:
		analyze(text)