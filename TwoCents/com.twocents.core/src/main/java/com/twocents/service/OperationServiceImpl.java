package com.twocents.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.twocents.core.util.CoreUtil;
import com.twocents.dao.OperationDAO;
import com.twocents.exceptions.CoreException;
import com.twocents.model.Account;
import com.twocents.model.Buy;
import com.twocents.model.CallSell;
import com.twocents.model.Operation;
import com.twocents.model.OperationView;
import com.twocents.model.Option;
import com.twocents.model.Sell;
import com.twocents.model.Stock;
import com.twocents.model.StockOperation;

@SuppressWarnings("unchecked")
public class OperationServiceImpl extends
BaseServiceImpl<Operation, OperationDAO> implements OperationService {

    private StockService stockService;
    private CustodyService custodyService;
    private AccountService accountService;

    private static final Logger log = Logger
            .getLogger(OperationServiceImpl.class);

    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public StockService getStockService() {
        return stockService;
    }

    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    public CustodyService getCustodyService() {
        return custodyService;
    }

    public void setCustodyService(CustodyService custodyService) {
        this.custodyService = custodyService;
    }

    public void setOperationDAO(OperationDAO dao) {
        super.setDao(dao);
    }

    public OperationDAO getOperationDAO() {
        return super.getDao();
    }

    public void sellOperation(Sell operation) throws CoreException {
        processStockOperation(operation);
        // log.info("Brokerage: " + operation.getBrokerage());
        // Account account = operation.getAccount();
        // account.setBalance
        // (
        // account.getBalance() - (operation.getAmount() * operation.getPrice())
        // );
        // accountService.persist(account);
        // Double profit = custodyService.removeCustody(operation);
        // operation.setProfit(profit);
        super.persist(operation);
    }

    /**
     *
     * @param operation
     * @throws CoreException
     */
    public void buyOperation(Buy operation) throws CoreException {
        processStockOperation(operation);
        // log.info("Brokerage: " + operation.getBrokerage());
        // Account account = operation.getAccount();
        //
        // account.setBalance(account.getBalance() - (operation.getAmount() *
        // operation.getPrice()));

        Stock stock = operation.getStock();
        if (stock instanceof Option) {
            stockService.updateOptionStock((Option) stock);
        }
        // custodyService.addCustody(operation);
        // accountService.persist(account);
        super.persist(operation);
    }

    /**
     *
     * @param operation
     * @throws CoreException
     */
    private void processStockOperation(StockOperation operation)
            throws CoreException {
        Stock stock = operation.getStock();
        if (stock != null && stock.getId() == null) {
            log.debug("Não existe ativo por esse codigo");
            Stock stockByCode = stockService.findStockByCode(stock.getCode());
            if (stockByCode == null) {
                stockByCode = stockService.addStock(stock);
            }
            stock = stockByCode;
        } else if (stock == null) {
            throw new CoreException(2001);
        }
        operation.setStock(stock);

        // Account account = operation.getAccount();
        //
        // if(account == null || account.getId() == null) {
        // throw new CoreException(2002);
        // }

        if (operation.getAmount() == null || operation.getAmount() < 1) {
            throw new CoreException(2003);
        }

        if (operation.getPrice() == null || operation.getPrice() < 0) {
            throw new CoreException(2004);
        }

        // boolean isDayTrade = verifyDayTrade(operation);
        // log.debug("Is Day Trade ? " + isDayTrade);
        // operation.setDayTrade(isDayTrade);

    }

    private boolean verifyDayTrade(StockOperation operation) {
        if (!(operation instanceof CallSell)) {
            Date date = new Date();
            Timestamp minDate = CoreUtil.getMinTimestamp(date);
            Timestamp maxDate = CoreUtil.getMaxTimestamp(date);
            Long totalBuy = this.findTotalBuyByStock(operation.getAccount(),
                    operation.getStock(), minDate, maxDate);
            if (totalBuy == null) {
                totalBuy = 0l;
            }
            Long totalSell = this.findTotalSellByStock(operation.getAccount(),
                    operation.getStock(), minDate, maxDate);
            if (totalSell == null) {
                totalSell = 0l;
            }
            Long totalSellCall = getOperationDAO().findTotalByStockDate(
                    operation.getAccount(), operation.getStock(), minDate,
                    maxDate, CallSell.class);
            if (totalSellCall == null) {
                totalSellCall = 0l;
            }
            if (operation instanceof Sell) {
                return (totalBuy - totalSell) >= operation.getAmount();
            } else {
                return (totalSell + totalSellCall - totalBuy) >= operation
                        .getAmount();
            }
        }
        return false;
    }

    private Long findTotalSellByStock(Account account, Stock stock,
            Timestamp start, Timestamp end) {
        return getOperationDAO().findTotalByStockDate(account, stock, start,
                end, Sell.class);
    }

    private Long findTotalBuyByStock(Account account, Stock stock,
            Timestamp start, Timestamp end) {
        return getOperationDAO().findTotalByStockDate(account, stock, start,
                end, Buy.class);
    }

    public void removeOperation(Operation operation) throws CoreException {
        operation = super.findById(operation.getId());
        log.info("Removendo operação: " + operation);

        log.info("Atualizando custodia se necessário");
        try{
            custodyService.updateCustodyRemovedOperation(operation);
        }catch(CoreException e){
            log.error("Falhou em atualizar a custodia",e);
        }
        log.info("Deleting operation in db");
        super.delete(operation);
    }

    public List<Operation> findOperationByAccount(Account account)
            throws CoreException {
        return getOperationDAO().findOperationByAccount(account);
    }

    public List<Map<String, Object>> findOperationMapByAccount(Account account,
            Timestamp starDate, Timestamp endDate) throws CoreException {
        return getOperationDAO().findOperationMapByAccount(account, starDate,
                endDate);
    }

    public List<OperationView> findOperationViewByAccount(Account account,
            Timestamp starDate, Timestamp endDate) {
        return getOperationDAO().findOperationViewByAccount(account, starDate,
                endDate);
    }

    public List<Map<String, Object>> findOperationMapByAccount(Account account,
            Timestamp starDate, Timestamp endDate,
            Class<? extends Operation> classOperation) throws CoreException {
        return getOperationDAO().findOperationMapByAccount(account, starDate,
                endDate, classOperation);
    }

    public void callSellOperation(CallSell operation) throws CoreException {
        processStockOperation(operation);
        Stock stock = operation.getStock();
        if (!(stock instanceof Option)) {
            throw new CoreException(2007);
        }
        super.persist(operation);
        // log.info("Brokerage: " + operation.getBrokerage());
        Account account = operation.getAccount();

        account.setBalance(account.getBalance() - operation.getAmount()
                * operation.getPrice());

        accountService.persist(account);
        stockService.updateOptionStock((Option) stock);
        custodyService.blockCustody(operation);
    }

    public List<Buy> findBuyByAccount(Account account) throws CoreException {
        return (List<Buy>) getOperationDAO().findOperationByAccount(account,
                Buy.class);
    }

    public List<CallSell> findCallSellByAccount(Account account)
            throws CoreException {
        return (List<CallSell>) getOperationDAO().findOperationByAccount(
                account, CallSell.class);
    }

    public List<Sell> findSellByAccount(Account account) throws CoreException {
        return (List<Sell>) getOperationDAO().findOperationByAccount(account,
                Sell.class);
    }

    public List<StockOperation> findStockOperationByAccount(Account account)
            throws CoreException {
        return (List<StockOperation>) getOperationDAO().findOperationByAccount(
                account, StockOperation.class);
    }

    public List<Map<String, Object>> findStockOperationByAccountAndInterval(
            Account account, Timestamp dateStart, Timestamp dateEnd)
                    throws CoreException {

        List<Map<String, Object>> listMap = findOperationMapByAccount(account,
                dateStart, dateEnd);

        return listMap;
    }

    public List<Map<String, Object>> findOperationByAccountWithDateOrdered(
            Account account, Timestamp starDate, Timestamp endDate)
                    throws CoreException {
        return getOperationDAO().findOperationByAccountWithDateOrdered(account,
                starDate, endDate);
    }


    public List<Map<String, Object>> findOperationByAccountWithDateOrderedAsc(
            Account account, Timestamp starDate, Timestamp endDate)
                    throws CoreException {
        return getOperationDAO().findOperationByAccountWithDateOrderedAsc(
                account, starDate, endDate);

    }


    public List<Map<String, Object>> findOperationByAccountWithDateOrderedDesc(
            Account account, Timestamp starDate, Timestamp endDate)
                    throws CoreException {
        return getOperationDAO().findOperationByAccountWithDateOrderedDesc(
                account, starDate, endDate);

    }

    public void registerOperation(Operation operation) throws CoreException {

        if (operation instanceof Buy) {
            buyOperation((Buy) operation);
        } else if (operation instanceof Sell) {
            sellOperation((Sell) operation);
        } else if (operation instanceof CallSell) {
            callSellOperation((CallSell) operation);
        }
    }

    public List<Map<String, Object>> findOperationByAccountWithDateOrderedAsc(
            Account account) throws CoreException {
        return getOperationDAO().findOperationByAccountWithDateOrderedAsc(
                account);
    }

    public Long findAmountOfStock(Account account, Stock stock,
            Timestamp startDate, Timestamp endDate) {
        return getOperationDAO().findAmountOfStock(account, stock, startDate,
                endDate);
    }

    public List<Integer> findYearOfOperations(Account account)
            throws CoreException {
        return getOperationDAO().findYearOfOperations(account);
    }
}
