from transformers import AutoTokenizer, AutoModelForCausalLM
import sqlite3

def main(user_model_conversation_history: list[str], path_to_database: str):
    path_to_database = path_to_database
    default_model_task = "-- Task: Using valid SQLite, answer the following questions for the tables provided above.\n\n"


    def create_database_schema():
        conn = sqlite3.connect(path_to_database)

        cur = conn.cursor()

        # Execute a SQL query to retrieve all table names in the database
        cur.execute("SELECT name FROM sqlite_master WHERE type='table';")

        # Fetch all results of the query
        tables = cur.fetchall()

        # Iterate through the results and print the table names along with their column names and types
        database_schema = ""
        for table in tables:
            table_name = table[0]

            # Execute a query to get the column information for the current table
            cur.execute(f"PRAGMA table_info({table_name});")

            # Fetch all column information
            columns = cur.fetchall()

            # Extract column names and types and format the string
            column_info = ', '.join([f"{column[1]} {column[2]}" for column in columns])
            formatted_string = f"CREATE TABLE {table_name}({column_info})\n\n"
            database_schema += formatted_string


        # Close the cursor and the connection
        cur.close()
        conn.close()

        return database_schema


    def xxx():
        pre_prompt = ""
        for i in range(len(user_model_conversation_history)):
            if i%2 == 0:
                pre_prompt += default_model_task
            pre_prompt += user_model_conversation_history[i]
            pre_prompt += "\n\n"

        return pre_prompt
    #database_schema = create_database_schema()

    prompt=''
    prompt += create_database_schema()
    prompt += xxx()
    prompt += 'SELECT'


    def call_model(prompt):
        model_name = "NumbersStation/nsql-350M"
        tokenizer = AutoTokenizer.from_pretrained(model_name)
        model = AutoModelForCausalLM.from_pretrained(model_name)
        input_ids = tokenizer(prompt, return_tensors="pt").input_ids
        generated_ids = model.generate(input_ids, max_length=1500)
        output = tokenizer.decode(generated_ids[0], skip_special_tokens=True)
        output = 'SELECT' + output.split('SELECT')[-1]

        return output

    print(call_model(prompt))

