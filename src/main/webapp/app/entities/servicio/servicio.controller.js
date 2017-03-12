(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('ServicioController', ServicioController);

    ServicioController.$inject = ['$scope', '$state', 'Servicio'];

    function ServicioController ($scope, $state, Servicio) {
        var vm = this;

        vm.servicios = [];

        loadAll();

        function loadAll() {
            Servicio.query(function(result) {
                vm.servicios = result;
                vm.searchQuery = null;
            });
        }
    }
})();
