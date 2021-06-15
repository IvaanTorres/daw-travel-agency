USE packsturisticos;

INSERT INTO tipos(codigo, nombre) VALUES
(01, 'Alojamiento'),
(02, 'Ocio'),
(03, 'Restaurante');



INSERT INTO actividades(id, nombre, descripcion, imagen, url, calidad, tipo, precio, ubicacion) VALUES

(01, 'Museo Picasso Málaga', '¿Te gusta el arte Renacentista? Disfruta de esta experiencia donde las doscientas ochenta y cinco obras que reúne esta colección abarcan las innovaciones revolucionarias de Picasso, así como la amplia variedad de estilos, materiales y técnicas que dominó.',
'museoPicasso.jpg', 'https://www.museopicassomalaga.org/', 4,
02, 12, 'https://goo.gl/maps/U2mNTBzMkzn22CGR8'),
(02, 'Catedral Málaga',
'La Santa Iglesia Catedral Basílica de la Encarnación es la catedral de Málaga, en España. Situada enfrente de la plaza del Obispo, el templo es considerado una de las joyas renacentistas más valiosas de Andalucía.',
'catedral-malaga.jpg', 'https://malagacatedral.com/visita-cultural/visita-a-la-catedral/',
 4, 02, 6, 'https://goo.gl/maps/HezePuvwgFLVTj6r8'),
 (03, 'Jardín botánico La Concepción',
 'El Jardín Botánico Histórico La Concepción es un jardín de estilo paisajista inglés con más de ciento cincuenta años de historia. Situado en la entrada norte de la ciudad de Málaga, se trata de uno de los escasos jardines con plantas de clima subtropical que existen en Europa.',
 'jardin-botanico.jpg', 'https://laconcepcion.malaga.eu/',
  4, 02, 5.20, 'https://goo.gl/maps/MWFP1joP2dsK3k9h7'),
(04, 'Hotel NH Málaga','Este elegante hotel se encuentra en un edificio de estilo moderno a la orilla del río Guadalmedina. Se sitúa a 11 minutos a pie de la catedral del Málaga, del siglo XVI, y a 1,3 km de la Alcazaba, la fortaleza medieval.',
'nh-malaga.jpg',
'https://www.nh-hoteles.es/hotel/nh-malaga',
4, 01, 80, 'https://g.page/NHMalaga?share'),
(05, 'Hotel Soho las Vegas',
 'Este moderno hotel, con vistas a la playa de la Malagueta, se encuentra a 1 minuto a pie de la parada de autobús de Paseo de Sancha y a 1 km de la Alcazaba de Málaga, una fortaleza medieval.',
'sohoHotel.jpg', 'https://www.sohohoteles.com/hotel-soho-vegas-en-malaga/',
 3, 01, 65, 'https://goo.gl/maps/pEi9cjwcgmFRzQFdA'),
 (06, 'Debambú Larios',
 'El Debambu Larios está ubicado en Málaga, a 100 metros del mercado de Atarazanas y a 2,4 km de la playa de la Misericordia, y ofrece alojamiento con WiFi gratuita y TV de pantalla plana.',
  'debambuApartamento.jpg', 'http://www.debambu.es/rooms/apartamento-larios/',
  4, 01, 70, 'https://goo.gl/maps/5vn6RQWi5M9ERhpz8'),
  (07, 'Terra Mia', 'Pizzas y recetas clásicas de la cocina tradicional napolitana en un alegre y colorido restaurante-pizzería.',
 'terra-mia.jpg', 'https://terramiamalaga.com/',
  4, 03, 10, 'https://goo.gl/maps/kBbbXpUQo3DxrPco7'),
  (08, 'Tercer Acto', 'Gastronomía & aplausos. El teatro es lo más parecido a la vida, y comer lo más parecido al teatro: entremeses,primer acto, segundo acto... y el tercer acto, donde uno, jubiloso, aplaude satisfecho en su interior',
  'tercerActo.jpg',
 'https://www.terceractogastro.com/', 
 5, 03, 20, 'https://g.page/terceracto_gastro?share'),
  (09, 'La Alacena de Francis',
 'Amplia tapería delicatessen Francis, una larga trayectoria avala el prestigio y calidad a este espléndido restaurante, situado en el barrio más amplio y castizo de nuestra Málaga "EL PERCHEL".',
'alacenaFrancis.jpg', 'http://laalacenadefrancis.com/',
 5, 03, 20, 'https://goo.gl/maps/qsXtJ4BFVcxQXWZu7'),
 (10, 'Hotel Brö', '​Este hotel sencillo solo para adultos, ubicado en una calle estrecha llena de tiendas, se encuentra a 15 minutos a pie de la playa de La Malagueta junto al mar Mediterráneo y a 3 km de la estación de tren Málaga María Zambrano.',
  'hotelBro.jpg', 'https://hotelbro.com/', 3, 01, 60, 'https://g.page/hotelbro?share'),
  (11, 'Hotel Posadas de España Málaga', 'Este hotel, que ocupa un edificio con fachada de ladrillo rojo, se encuentra a 10 km del Jardín Botánico y a 14 del frondoso Jardín Oriental Bienquerido.',
  'hotelPosada.jpg', 'https://www.posadasdeespanamalaga.com/', 3, 1, 55, 'https://goo.gl/maps/8miS3A5pgzoecF9R6'),
  (12, 'Blossom', 'Informal, cálido, moderno y elegante. Saborea y descubre nuestra propuesta culinaria destinada a sibaritas de todo el mundo. Cocina contemporánea.',
  'restauranteBlossom.jpg', 'https://blossommalaga.com/menu/', 5, 03, 30, 'https://g.page/blossomrestaurante?share'),
  (13, 'Sabor a Nápoles', 'En este acogedor restaurante podrás teletransportarte al magnífico sabor típico italiano. Disfruta de nuestra gastronomía variada.',
  'restauranteSN.jpg', 'https://saboranapoles.negocio.site/', 3, 03, 15, 'https://g.page/Saboranapoles?share'),
  (14, 'AquaMijas', 'Una docena de atracciones para todas las edades en un espacio único en la Costa del Sol, entre Mijas y Fuengirola. ¡Pásalo en grande en AquaMijas! Diversión asegurada en la Costa del Sol.',
   'aquamijas.jpg', 'https://aquamijas.com/', 3, 02, 22, 'https://g.page/parqueaquamijas?share'),
  (15, 'SouldPark Fuengirola', 'Sould Park, parques de atracciones infantiles por todo el territorio español. Ocio asegurado para toda la familia. ¡Descubre tu Sould Park más cercano!',
  'souldpark.jpg', 'https://www.souldpark.com/', 2, 02, 10, 'https://goo.gl/maps/9ZhLBq8w9fausjmH9')
;
/*!40000 ALTER TABLE `actividades` DISABLE KEYS */;

INSERT INTO usuarios VALUES (1, "admin", "admin", "admin@gmail.com", 21212121, 21212121, "21232f297a57a5a743894a0e4a801fc3");

