-- Users Table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'STUDENT', -- STUDENT, TEACHER
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Docs
CREATE TABLE documents (
    id SERIAL PRIMARY KEY,
    owner_id INTEGER REFERENCES users(id) ON DELETE CASCADE, 
    title VARCHAR(255) NOT NULL,
    file_path VARCHAR(500) NOT NULL, --file path on server or s3
    file_type VARCHAR(10),	-- pdf, docx, jpg
    status VARCHAR(20) DEFAULT 'PROCESSING', -- UPLOADED, PROCESSING, READY, ERROR
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- analyze python
CREATE TABLE document_summaries (
    id SERIAL PRIMARY KEY,
    document_id INTEGER REFERENCES documents(id) ON DELETE CASCADE,
    full_text TEXT,		-- full export text
    summary_context TEXT, --summaries from ai
    key_terms JSONB,	-- list keys json format
    generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- test  (quiz)
CREATE TABLE quizzes (
    id SERIAL PRIMARY KEY,
    document_id INTEGER REFERENCES documents(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- questions users
CREATE TABLE questions (
    id SERIAL PRIMARY KEY,
    quiz_id INTEGER REFERENCES quizzes(id) ON DELETE CASCADE,
    question_text TEXT NOT NULL, 
    question_type VARCHAR(20) DEFAULT 'MULTIPLE_CHOICE' -- MULTIPLE_CHOICE, TRUE_FALSE
);

-- answers users
CREATE TABLE answers (
    id SERIAL PRIMARY KEY,
    question_id INTEGER REFERENCES questions(id) ON DELETE CASCADE, 
    answer_text TEXT NOT NULL,
    is_correct BOOLEAN DEFAULT FALSE
);

-- attempts
CREATE TABLE user_quiz_attempts (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    quiz_id INTEGER REFERENCES quizzes(id) ON DELETE CASCADE,
    score INTEGER NOT NULL, -- amount score %
    completed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- index for fast search
CREATE INDEX idx_documents_owner ON documents(owner_id);
CREATE INDEX idx_summaries_document ON document_summaries(document_id);
CREATE INDEX idx_questions_quiz ON questions(quiz_id);