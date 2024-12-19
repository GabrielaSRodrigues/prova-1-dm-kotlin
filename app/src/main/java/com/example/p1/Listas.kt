package com.example.p1

class Listas(cinema : ArrayList<Cinema>, posto : ArrayList<Posto>,supemercado : ArrayList<Supermercado>) {
    var supermercados : ArrayList<Supermercado>
    var cinemas : ArrayList<Cinema>
    var postos : ArrayList<Posto>
    init {
        this.cinemas = cinema
        this.postos = posto
        this.supermercados = supemercado
    }
}