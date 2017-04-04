(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('TipoHeaderController', TipoHeaderController);

    TipoHeaderController.$inject = ['$scope', '$state', 'TipoHeader'];

    function TipoHeaderController ($scope, $state, TipoHeader) {
        var vm = this;

        vm.tipoHeaders = [];

        loadAll();

        function loadAll() {
            TipoHeader.query(function(result) {
                vm.tipoHeaders = result;
                vm.searchQuery = null;
            });
        }
    }
})();
