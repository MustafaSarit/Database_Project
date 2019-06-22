select * from countrytostat;
select * from country;
select * from stat;
select * from hdi.groups;
select * from region;

#View
CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `ui_view` AS
    SELECT 
        `c`.`CountryName` AS `Country`,
        `cs`.`Value` AS `HDI Rank`,
        `r`.`Value` AS `HDI`
    FROM
        (`countrytostat` `cs`
        JOIN (`country` `c`
        JOIN (SELECT 
            `countrytostat`.`CountryId` AS `CountryId`,
                `countrytostat`.`StatId` AS `StatId`,
                `countrytostat`.`Value` AS `Value`
        FROM
            `countrytostat`
        WHERE
            (`countrytostat`.`StatId` = 2)) `r` ON ((`r`.`CountryId` = `c`.`CountryId`))))
    WHERE
        ((`c`.`CountryId` = `cs`.`CountryId`)
            AND (`cs`.`StatId` = 1));
  

#Store Procedures
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `getCountryStats`(in cName VARCHAR(45))
BEGIN
select s.StatTypeName, cs.Value from country c, countrytostat cs, stat s 
where c.CountryId = cs.CountryId and cs.StatId = s.StatId and c.CountryName = cName;
END
//

DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `MostGNIDifference`(OUT country INT)
BEGIN
select r.d as "Difference" into country
from country as c inner join
(select m.CountryId, m.value as mi, w.value as wi,  m.value - w.value as d
from countrytostat as m inner join
(select * from countrytostat where statId = 20) as w on m.CountryId = w.CountryId 
where m.statId = 21 order by (m.value - w.value) desc limit 1) as r on c.CountryId = r.CountryId;
END
//

DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `worstCountryOfRegion`(inout region VARCHAR(45))
BEGIN
select CountryName into region from countrytostat as cs, country as c 
where statID = 1 and c.CountryId = cs.CountryID 
and c.CountryId in
(select CountryID from region as r, country as c where RegionName = region 
and r.RegionId = c.RegionId) order by cs.Value desc limit 1;
END
//



# Q1:	What is the average human development index of the world?
select avg(countrytostat.Value) from countrytostat where StatId = 2;

# Q2:	What is average of the human lifespan?
select avg(countrytostat.value) from countrytostat where StatId = 3;

# Q3:	Which country has the maximum difference of gross national income between man and women?
select c.CountryId, c.CountryName, r.mi as "Men Incomes", r.wi as "Women Incomes", r.d as "Difference" from country as c inner join
(select m.CountryId, m.value as mi, w.value as wi,  m.value - w.value as d
from countrytostat as m inner join
(select * from countrytostat where statId = 20) as w on m.CountryId = w.CountryId 
where m.statId = 21 order by (m.value - w.value) desc limit 1) as r on c.CountryId = r.CountryId;

# Q4: 	What is the mean of public health expenditure percentage of gdp?
select avg(countrytostat.value) from countrytostat where StatId = 52;

# Q5:	What is the mean year of schooling?
select avg(countrytostat.value) from countrytostat where StatId = 4;

# Q6:	What is the average of child labor percentage of the world?
select avg(countrytostat.value) from countrytostat where StatId = 61;

# Q7:	What is the average internet user percentage of the world?
select avg(countrytostat.value) from countrytostat  where StatId = 65;

# Q8:	What is the distribution of countries in human development groups? 
select GroupName, count(country.GroupId) from country, hdi.groups 
	where hdi.groups.GroupId = country.GroupId group by country.GroupId order by count(country.GroupId);

# Q9:	What will be the growth in population of countries between 2015-2030?
select distinct (select sum(countrytostat.value) from countrytostat where StatId = 27) - 
(select sum(countrytostat.value) from countrytostat where StatId = 26) as "Total population difference" from countrytostat;

# Q10:	What is the HDI rank of the top 5 least income inequality of countries?
select v.CountryName, countrytostat.value as "HDI Rank" from countrytostat, country as v inner join
(select * from countrytostat where statId = 75 order by countrytostat.value desc limit 5) as v1 on v.CountryId = v1.CountryId 
where v1.CountryId = countrytostat.CountryId and countrytostat.StatID = 1;

# View
select * from ui_view;

# Store procs
# IN
select CountryName from country;
CALL getCountryStats("Turkey");

# OUT
CALL MostGNIDifference(@mCountry);
select @mCountry as "The most GNI differenece between men and women in a country";


# INOUT
select RegionName from region;
set @wCountry = "Arab States";
CALL worstCountryOfRegion(@wCountry);
select @wCountry as "The worst country of the region";