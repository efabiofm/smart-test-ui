(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('AmbienteController', AmbienteController);

    AmbienteController.$inject = ['$scope', '$state', 'Ambiente'];

    function AmbienteController ($scope, $state, Ambiente) {
        var vm = this;

        vm.ambientes = [];

        loadAll();

        function loadAll() {
            Ambiente.query(function(result) {
                vm.ambientes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
