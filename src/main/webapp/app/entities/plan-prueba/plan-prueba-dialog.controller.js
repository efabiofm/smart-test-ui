(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('PlanPruebaDialogController', PlanPruebaDialogController);

    PlanPruebaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PlanPrueba', 'Prueba'];

    function PlanPruebaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PlanPrueba, Prueba) {
        var vm = this;

        vm.planPrueba = entity;
        vm.clear = clear;
        vm.save = save;
        vm.pruebas = Prueba.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.planPrueba.id !== null) {
                PlanPrueba.update(vm.planPrueba, onSaveSuccess, onSaveError);
            } else {
                PlanPrueba.save(vm.planPrueba, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smartTestUiApp:planPruebaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
