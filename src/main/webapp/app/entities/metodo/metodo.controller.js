(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('MetodoController', MetodoController);

    MetodoController.$inject = ['$scope', '$state', 'Metodo'];

    function MetodoController ($scope, $state, Metodo) {
        var vm = this;

        vm.metodos = [];

        loadAll();

        function loadAll() {
            Metodo.query(function(result) {
                vm.metodos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
