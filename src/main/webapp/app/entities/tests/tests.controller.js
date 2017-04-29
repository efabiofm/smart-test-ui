(function () {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('JhiTestsController', JhiTestsController);

    JhiTestsController.$inject = ['EjecucionPrueba'];

    //controller for all the tests executions
    function JhiTestsController (EjecucionPrueba) {
        var vm = this;
        vm.filtrar = '';

        vm.pruebas = [];

        loadEjecuciones();
        refreshEjecuciones();

        //  Obtains all the ejecuciones and puts them on different lists so they can be shown accordingly
        function loadEjecuciones(){
            EjecucionPrueba.query().$promise.then(function (resultado){
                vm.pruebasPendientes = [];
                vm.pruebasCompletadas = [];
                for(var index in resultado){
                    var prueba = resultado[index];
                    if(prueba.estado === "Pendiente"){
                        vm.pruebasPendientes.push(prueba);
                    }else if(prueba.estado === "Pass" || prueba.estado === "Fail"){
                        vm.pruebasCompletadas.push(prueba);
                    }
                }
            });
        }

        // refreshes all the ejecuciones every 2 seconds
        function refreshEjecuciones() {
            setInterval(loadEjecuciones, 2000);
        }

        vm.tests = [];

    }
})();
