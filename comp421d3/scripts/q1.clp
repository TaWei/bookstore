--For each Canadian province, list all of the distinct cities that belong to this province (i.e. have a user living there or is a destination for a shipment).
xquery \
<provinces> { \
  for $province in  distinct-values(db2-fn:xmlcolumn('ADDRESS.ADDRINFO')/addrInfo[fn:data(country)="CAN"]/province) \    
  let $cities := distinct-values(db2-fn:xmlcolumn('ADDRESS.ADDRINFO')/addrInfo[fn:data(province)=fn:data($province)]/city) \
  return \
    <province> \
      <name>{fn:data($province)}</name> \
      <cities>{fn:data($cities)}</cities> \
    </province> \
} </provinces>
  

