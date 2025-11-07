use locationdb

db.city.insertMany([
  { city:"pune", type:"urban", state:"MH", population:5600000 },
  { city:"mumbai", type:"urban", state:"MH", population:12400000 },
  { city:"nagpur", type:"urban", state:"MH", population:2400000 },
  { city:"nashik", type:"urban", state:"MH", population:1500000 },
  { city:"satara", type:"rural", state:"MH", population:750000 },
  { city:"ahmedabad", type:"urban", state:"GJ", population:5570000 },
  { city:"surat", type:"urban", state:"GJ", population:4460000 },
  { city:"vadodara", type:"urban", state:"GJ", population:2060000 },
  { city:"rajkot", type:"urban", state:"GJ", population:1600000 },
  { city:"kheda", type:"rural", state:"GJ", population:810000 }
])


var mapFunction = function() {
    emit(this.key, this.population);
};


var reduceFunction = function(key, values) {
    return Array.sum(values);
};


db.city.mapReduce(
    function() { emit(this.state, this.population); },
    function(key, values) { return Array.sum(values); },
    { out: "state_population" }
)


db.state_population.find()


db.city.mapReduce(
    function() { emit(this.city, this.population); },
    function(key, values) { return Array.sum(values); },
    { out: "city_population" }
)

db.city_population.find()


db.city.mapReduce(
    function() { emit(this.type, this.population); },
    function(key, values) { return Array.sum(values); },
    { out: "type_population" }
)

db.type_population.find()



