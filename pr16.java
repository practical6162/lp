use employee

db.computer.insertMany([
  { emp_id: 1, name: "Amit", designation: "Developer", Salary: 40000 },
  { emp_id: 2, name: "Ravi", designation: "Tester", Salary: 35000 },
  { emp_id: 3, name: "Sneha", designation: "Manager", Salary: 60000 },
  { emp_id: 4, name: "Priya", designation: "Developer", Salary: 45000 },
  { emp_id: 5, name: "Karan", designation: "Admin", Salary: 30000 }
])


var mapFunction = function() {
  emit("total", this.Salary);
};


var reduceFunction = function(key, values) {
  return Array.sum(values);
};


db.computer.mapReduce(
  mapFunction,
  reduceFunction,
  { out: "total_salary" }
)


db.total_salary.find().pretty()


