package com.firstflip.supermarket.model;

public enum CategoriaProducto {

  ELECTRONICA_TECNOLOGIA("Dispositivos digitales, componentes de PC, redes y accesorios móviles."),

  HOGAR_COCINA(
      "Electrodomésticos, utensilios de cocina, artículos de limpieza y sistemas de seguridad inteligentes para el hogar."),

  MUEBLES_OFICINA(
      "Mobiliario para trabajo y descanso, soluciones de almacenamiento y decoración exterior."),

  AUDIO_VIDEO(
      "Televisores, equipos de sonido, instrumentos musicales y todo lo relacionado con la producción y consumo multimedia."),

  FOTOGRAFIA_DRONES(
      "Cámaras, lentes, accesorios fotográficos y vehículos aéreos no tripulados (drones)."),

  DEPORTES_OCIO(
      "Equipamiento para fitness, material deportivo, camping y artículos para actividades al aire libre."),

  LIBROS_ARTE("Material de lectura, pintura, dibujo y otros suministros para hobbies creativos."),

  JARDINERIA_BRICOLAJE(
      "Herramientas, maquinaria, insumos para el jardín y materiales de construcción o reparación doméstica."),

  JUGUETES_JUEGOS(
      "Artículos para entretenimiento infantil y adulto, incluyendo consolas, juegos de mesa y construcción."),

  MODA_CUIDADO_PERSONAL(
      "Ropa, calzado, accesorios de viaje y productos de belleza, higiene y afeitado.");

  private final String descripcion;

  public String getDescripcion() {
    return descripcion;
  }


  private CategoriaProducto(String description) {
    this.descripcion = description;
  }
}
