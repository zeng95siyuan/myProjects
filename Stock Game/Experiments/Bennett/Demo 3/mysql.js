var mysql = require('mysql');

let stock = {
    id: 10,
    name: "NFLX",
    value: 45.98
};

var con = mysql.createConnection({
    host: "cs309-jr-4.misc.iastate.edu",
    user: "user",
    password: "Teamjr_4password",
    database: "stock_game",
    port: 3306
});

con.connect(function(err){
    if(err) throw err;
    console.log("Connected");
});

var sql = `INSERT INTO stock (id, stock_name, current_value) VALUES (${stock.id}, "${stock.name}", ${stock.value})`;
console.log(sql);
con.query(sql, function(err, results){
    if(err) throw err;
}); 

con.end();