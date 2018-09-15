import { element, by, ElementFinder } from 'protractor';

export class EntradaComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-entrada div table .btn-danger'));
    title = element.all(by.css('jhi-entrada div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getText();
    }
}

export class EntradaUpdatePage {
    pageTitle = element(by.id('jhi-entrada-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    clienteInput = element(by.id('field_cliente'));
    valorTotalInput = element(by.id('field_valorTotal'));
    valorPagoInput = element(by.id('field_valorPago'));
    dataInput = element(by.id('field_data'));
    formaPagamentoSelect = element(by.id('field_formaPagamento'));
    corSelect = element(by.id('field_cor'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setClienteInput(cliente) {
        await this.clienteInput.sendKeys(cliente);
    }

    async getClienteInput() {
        return this.clienteInput.getAttribute('value');
    }

    async setValorTotalInput(valorTotal) {
        await this.valorTotalInput.sendKeys(valorTotal);
    }

    async getValorTotalInput() {
        return this.valorTotalInput.getAttribute('value');
    }

    async setValorPagoInput(valorPago) {
        await this.valorPagoInput.sendKeys(valorPago);
    }

    async getValorPagoInput() {
        return this.valorPagoInput.getAttribute('value');
    }

    async setDataInput(data) {
        await this.dataInput.sendKeys(data);
    }

    async getDataInput() {
        return this.dataInput.getAttribute('value');
    }

    async setFormaPagamentoSelect(formaPagamento) {
        await this.formaPagamentoSelect.sendKeys(formaPagamento);
    }

    async getFormaPagamentoSelect() {
        return this.formaPagamentoSelect.element(by.css('option:checked')).getText();
    }

    async formaPagamentoSelectLastOption() {
        await this.formaPagamentoSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setCorSelect(cor) {
        await this.corSelect.sendKeys(cor);
    }

    async getCorSelect() {
        return this.corSelect.element(by.css('option:checked')).getText();
    }

    async corSelectLastOption() {
        await this.corSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class EntradaDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-entrada-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-entrada'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
