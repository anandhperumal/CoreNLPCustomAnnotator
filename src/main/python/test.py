# Created by anandhperumal at 2021-06-13
from flask import Flask, jsonify, request
import gender_guesser.detector as gender
import json

app = Flask(__name__)
from transformers import AutoTokenizer, AutoModelForTokenClassification
from transformers import pipeline

tokenizer = AutoTokenizer.from_pretrained("dslim/bert-base-NER")
model = AutoModelForTokenClassification.from_pretrained("dslim/bert-base-NER")
gender_detector = gender.Detector()



@app.route('/')
def hello_world():
    return 'Hello World!'

@app.route('/GENDER', methods = ['POST'])
def gender():
    if request.method == 'POST':
        word = request.data.decode('utf-8')
        gender_detected = gender_detector.get_gender(word)
        template = "{}:{} \n".format(word, gender_detected)
        return template

@app.route('/NER', methods = ['POST'])
def ner():
    if request.method == 'POST':
        decoded_data = request.data.decode('utf-8')
        template = "{}:{} \n"
        output = ""

        nlp = pipeline("ner", model=model, tokenizer=tokenizer)
        example = request.data.decode('utf-8')

        ner_results = nlp(example)
        for result in ner_results:
            if 'PER' in result['entity']:
                result['entity'] = 'PERSON'
            elif 'ORG' in result['entity']:
                result['entity'] = 'ORGANIZATION'
            elif 'LOC' in result['entity']:
                result['entity'] = 'LOCATION'

            template = "{}:{} \n".format(result['word'], result['entity'])
            output += template
        return output

if __name__ == '__main__':
    app.run(debug=True)