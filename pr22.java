use orgDB


db.Employee.insertMany([
  { employee_name: "Amit", street: "MG Road", city: "Pune" },
  { employee_name: "Ravi", street: "FC Road", city: "Mumbai" },
  { employee_name: "Sneha", street: "JM Road", city: "Delhi" },
  { employee_name: "Karan", street: "MG Road", city: "Pune" },
  { employee_name: "Neha", street: "Park Street", city: "Delhi" },
  { employee_name: "Jones", street: "Hill Street", city: "Oldtown" }
])

db.Works.insertMany([
  { employee_name: "Amit", company_name: "First Bank Corporation", salary: 12000 },
  { employee_name: "Ravi", company_name: "Small Bank Corporation", salary: 9000 },
  { employee_name: "Sneha", company_name: "First Bank Corporation", salary: 15000 },
  { employee_name: "Karan", company_name: "TechSoft", salary: 8000 },
  { employee_name: "Neha", company_name: "First Bank Corporation", salary: 11000 },
  { employee_name: "Jones", company_name: "Small Bank Corporation", salary: 10000 }
])

db.Company.insertMany([
  { company_name: "First Bank Corporation", city: "Pune" },
  { company_name: "Small Bank Corporation", city: "Mumbai" },
  { company_name: "Small Bank Corporation", city: "Delhi" },
  { company_name: "TechSoft", city: "Pune" },
  { company_name: "TechSoft", city: "Delhi" }
])



var maxSmallBank = db.Works.aggregate([
  { $match: { company_name: "Small Bank Corporation" } },
  { $group: { _id: null, maxSalary: { $max: "$salary" } } }
]).toArray()[0].maxSalary;


db.Works.find({ salary: { $gt: maxSmallBank } }, { _id: 0, employee_name: 1, salary: 1 })


var smallBankCities = db.Company.distinct("city", { company_name: "Small Bank Corporation" });


db.Company.aggregate([
  { $match: { city: { $in: smallBankCities } } },
  { $group: { _id: "$company_name", cities: { $addToSet: "$city" } } },
  { $match: { $expr: { $setIsSubset: [smallBankCities, "$cities"] } } }
])


db.Works.aggregate([
  {
    $group: {
      _id: "$company_name",
      avgSalary: { $avg: "$salary" },
      employees: { $push: { name: "$employee_name", salary: "$salary" } }
    }
  },
  { $unwind: "$employees" },
  { $match: { $expr: { $gt: ["$employees.salary", "$avgSalary"] } } },
  { $project: { _id: 0, employee_name: "$employees.name", company_name: "$_id", salary: "$employees.salary", avgSalary: 1 } }
])



db.Works.aggregate([
  { $group: { _id: "$company_name", totalEmployees: { $sum: 1 } } },
  { $sort: { totalEmployees: -1 } },
  { $limit: 1 }
])



db.Works.aggregate([
  { $group: { _id: "$company_name", totalPayroll: { $sum: "$salary" } } },
  { $sort: { totalPayroll: 1 } },
  { $limit: 1 }
])



var firstBankAvg = db.Works.aggregate([
  { $match: { company_name: "First Bank Corporation" } },
  { $group: { _id: null, avgSalary: { $avg: "$salary" } } }
]).toArray()[0].avgSalary;



db.Works.aggregate([
  { $group: { _id: "$company_name", avgSalary: { $avg: "$salary" } } },
  { $match: { avgSalary: { $gt: firstBankAvg } } }
])



db.Employee.updateOne(
  { employee_name: "Jones" },
  { $set: { city: "Newtown" } }
)
