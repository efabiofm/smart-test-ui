'use strict';

describe('Controller Tests', function() {

    describe('Servicio Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockServicio, MockModulo, MockPrueba, MockMetodo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockServicio = jasmine.createSpy('MockServicio');
            MockModulo = jasmine.createSpy('MockModulo');
            MockPrueba = jasmine.createSpy('MockPrueba');
            MockMetodo = jasmine.createSpy('MockMetodo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Servicio': MockServicio,
                'Modulo': MockModulo,
                'Prueba': MockPrueba,
                'Metodo': MockMetodo
            };
            createController = function() {
                $injector.get('$controller')("ServicioDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'smartTestUiApp:servicioUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
