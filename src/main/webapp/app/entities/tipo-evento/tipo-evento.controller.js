(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('TipoEventoController', TipoEventoController);

    TipoEventoController.$inject = ['$scope', '$state', 'TipoEvento'];

    function TipoEventoController ($scope, $state, TipoEvento) {
        var vm = this;

        vm.tipoEventos = [];

        loadAll();

        function loadAll() {
            TipoEvento.query(function(result) {
                vm.tipoEventos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
