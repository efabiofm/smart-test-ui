(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('HeaderController', HeaderController);

    HeaderController.$inject = ['$scope', '$state', 'Header'];

    function HeaderController ($scope, $state, Header) {
        var vm = this;

        vm.headers = [];

        loadAll();

        function loadAll() {
            Header.query(function(result) {
                vm.headers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
