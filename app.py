# Import necesarry flask resources
from flask import *
from flask_restful import Resource, Api

import pymysql
import pymysql.cursors

import pymysql
import random
import base64
import os
import string


# create the flask application
app = Flask(__name__)

# create ab API object and pass our app to be the api
api = Api(app)

# set the default path for the image upload

UPLOAD_FOLDER = 'static/images'
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
# create a class resource that will hold the four methods/functions
# The methods are Post, Put, Delete and Get
# These methods corresponds to the CRUD function. create == Post, Read == Get, Update == Put and
# Delete == Delete
def db_connection():
    return pymysql.connect(host="kimaniben.mysql.pythonanywhere-services.com", user="kimaniben", database="kimaniben$default", password="Lab4modcom")


class CreateEmployee(Resource):
    # create == Post
    def post(self):
        # create a db connection
        connection = db_connection()

        # create a cursor
        cursor = connection.cursor()

        # request data from json
        data = request.json

        id_number = data["id_number"]
        username = data["username"]
        others = data["others"]
        salary = data["salary"]
        department = data["department"]

        # structure an insert sql query
        sql = "INSERT INTO `employees`(`id_number`, `username`, `others`, `salary`, `department`) VALUES (%s, %s, %s, %s, %s)"

        # create a variable that will hold all the data gotten from json
        allData = (id_number,username,others, salary,department)

        # excute the sql by use of the cursor
        cursor.execute(sql, allData)

        # commit the database transaction
        connection.commit()
        return jsonify({'Message': 'Employee Registered Successfully'})

class RetrieveEmployee(Resource):
    # Read == Get
    def get(self):
        # Establish a db connection
        connection = db_connection()

        # create a cursor with the dictinary cursors
        cursor = connection.cursor(pymysql.cursors.DictCursor)

        # structure a query to retrieve all the employees
        sql = "select * from employees"

        # execute the sql by use of the cursor
        cursor.execute(sql)

        # check whether your database is empty
        if cursor.rowcount == 0:
            return jsonify({"Message": "No records found"})
        else:
            allEmployees = cursor.fetchall()
            return jsonify(allEmployees)

class UpdateEmployee(Resource):
    # Update == Put
    def put(self):
        # establish a db connection
        connection = db_connection()

        # create a cursor
        cursor  = connection.cursor()

        # structure an sql for updating the salary of an employee
        sql = "UPDATE `employees` SET `salary` = %s WHERE `employees`.`id_number` = %s"

        # pick details of the two columns i.e id_number and salary from json
        data = request.json

        id_number = data["id_number"]
        salary = data["salary"]

        # create a variable that will hold the data from the json variables
        data = (salary, id_number)

        # use the cursor to execute
        cursor.execute(sql, data)

        # finish the db transaction
        connection.commit()
        return jsonify({"Message": "Salary Updated successfully"})

class RemoveEmployee(Resource):
    # Delete == Delete
    def delete(self):
        # establish db connection
        connection = db_connection()

        # create a cursor
        cursor  = connection.cursor()

        # structure an sql for updating the salary of an employee
        sql = "DELETE FROM employees WHERE `employees`.`id_number` = %s"


        data = request.json
        id_number = data["id_number"]


        # create a variable that will hold the data from the json variables
        data = (id_number)

        # use the cursor to execute
        cursor.execute(sql, data)

        # finish the db transaction
        connection.commit()

        return jsonify({"Message": "Employee Deleted Successfully"})

class Product (Resource):
    def get(self):
        connection = db_connection()
        sql = "select * from products"
        rt = request.url_root
        # try:
        cursor = connection.cursor(pymysql.cursors.DictCursor)
        cursor.execute(sql)
        if cursor.rowcount == 0:
            return jsonify({"message": " No record found"})
        else:
            products = cursor.fetchall()
            for prod in products:
                if prod['image_name']:
                    prod['image_name'] = f"{request.url_root}static/images/{prod['image_name']}"
            return jsonify(products)
        # except:
        #     return jsonify({"message": "An error occurred"})

    def post(self):
        data = request.json
        prod_name = data['prod_name']
        prod_cost = data['prod_cost']
        prod_desc = data['prod_desc']
        image_data = data['image']

        image_bytes = base64.b64decode(image_data)
        N = 7
        filename = ''.join(random.choices(string.ascii_uppercase +
                             string.digits, k=N))

        if not os.path.exists(app.config['UPLOAD_FOLDER']):
            os.makedirs(app.config['UPLOAD_FOLDER'])
        # image = Image(data=image_bytes)
        image_name = filename+'.png'  # You can generate a unique filename here if needed
        with open(os.path.join(app.config['UPLOAD_FOLDER'], image_name), 'wb') as f:
                f.write(image_bytes)

        # connection = pymysql.connect(host = "localhost", user = "root", password = "", database = "basil_sokogarden")
        connection = db_connection()
        cursor = connection.cursor()
        sql = "insert into products (prod_name, prod_cost, prod_desc, image_name) values (%s, %s, %s, %s)"

        # define some variables
        try:
            cursor.execute(sql, (prod_name, prod_cost, prod_desc, image_name))
            connection.commit()
            return jsonify({"message": "Record saved"})
        except:
            connection.rollback()
            return jsonify({'message': "Record not saved"})

# provide a way of accessing the employee resource(Endpoint/url)
api.add_resource(CreateEmployee, "/createEmployee")
api.add_resource(RetrieveEmployee, "/RetrieveEmployees")
api.add_resource(UpdateEmployee, "/UpdateEmployee")
api.add_resource(RemoveEmployee, "/RemoveEmployee")
api.add_resource(Product, "/products")


# app.run(debug=True)
