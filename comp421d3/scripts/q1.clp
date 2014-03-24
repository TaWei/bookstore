--The query finds all of the cities in Canadian provinces that we deliver to and also lists the number of distinct addresses in those cities.
xquery \
<provinces> { \
  for $province in  distinct-values(db2-fn:xmlcolumn('ADDRESS.ADDRINFO')/addrInfo[fn:data(country)="CAN"]/province) \    
  return \
    <province> \
      <name>{fn:data($province)}</name> \ 
      <cities> { \
        for $city in distinct-values(db2-fn:xmlcolumn('ADDRESS.ADDRINFO')/addrInfo[fn:data(province)=fn:data($province)]/city) \
        let $count := fn:count(db2-fn:xmlcolumn('ADDRESS.ADDRINFO')/addrInfo[fn:data(province)=fn:data($province) and fn:data(city)=fn:data($city)]) \
        return \
          <city> \
            <name>{fn:data($city)}</name> \
            <count>{fn:data($count)}</count> \
          </city> \
      } </cities> \
    </province> \
} </provinces>
    
