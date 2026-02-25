from fastapi import FastAPI, UploadFile

app = FastAPI()

@app.post("/analyze")
async def analyze_document(file: UploadFile):
    #Сюда логику напишите - Вадим и Саша
    return {"Summary": "Это кратко содержание", "questions":[]}
