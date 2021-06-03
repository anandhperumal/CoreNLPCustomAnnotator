from flask import Flask, jsonify, request
import json

app = Flask(__name__)

@app.route('/')
def hello_world():
    return 'Hello World!'

@app.route('/NER', methods = ['POST'])
def ner():
    if request.method == 'POST':
        decoded_data = request.data.decode('utf-8')
        template = "{}:{} \n"
        output = ""
        count = 0
        for data in decoded_data.split(" "):
            output += template.format(data, count)
            count += 1

        return output

if __name__ == '__main__':
    app.run(debug=True)