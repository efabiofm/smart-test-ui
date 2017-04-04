(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('PruebaController', PruebaController);

    PruebaController.$inject = ['$scope', '$state', 'Prueba'];

    function PruebaController ($scope, $state, Prueba) {
        var vm = this;

        vm.pruebas = [];

        loadAll();

        function loadAll() {
            Prueba.query(function(result) {
                vm.pruebas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
