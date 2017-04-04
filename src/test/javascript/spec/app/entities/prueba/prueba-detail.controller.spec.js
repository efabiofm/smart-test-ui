'use strict';

describe('Controller Tests', function() {

    describe('Prueba Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPrueba, MockAmbiente, MockModulo, MockServicio, MockMetodo, MockEjecucionPrueba, MockPlanPrueba;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPrueba = jasmine.createSpy('MockPrueba');
            MockAmbiente = jasmine.createSpy('MockAmbiente');
            MockModulo = jasmine.createSpy('MockModulo');
            MockServicio = jasmine.createSpy('MockServicio');
            MockMetodo = jasmine.createSpy('MockMetodo');
            MockEjecucionPrueba = jasmine.createSpy('MockEjecucionPrueba');
            MockPlanPrueba = jasmine.createSpy('MockPlanPrueba');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Prueba': MockPrueba,
                'Ambiente': MockAmbiente,
                'Modulo': MockModulo,
                'Servicio': MockServicio,
                'Metodo': MockMetodo,
                'EjecucionPrueba': MockEjecucionPrueba,
                'PlanPrueba': MockPlanPrueba
            };
            createController = function() {
                $injector.get('$controller')("PruebaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'smartTestUiApp:pruebaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
