package profiler;

import soot.*;
import soot.jimple.*;
import soot.util.*;

import java.util.*;

public class InvokeStaticInstrumenter extends BodyTransformer {

    static SootClass counterClass;
    static SootMethod increaseCounter, reportCounter, displayCounter;

    static {
        counterClass = Scene.v().loadClassAndSupport("profiler.MyCounter");
        increaseCounter = counterClass.getMethod("void increase(int)");
        reportCounter = counterClass.getMethod("void report()");
        displayCounter = counterClass.getMethod("void display()");
        Scene.v().setSootClassPath(null);
    }

    private void insertReportIfMain(SootMethod method, Chain units) {
        String signature = method.getSubSignature();
        boolean isMain = signature.equals("void main(java.lang.String[])");
        if (isMain) {
            Iterator stmtIt = units.snapshotIterator();
            while (stmtIt.hasNext()) {
                Stmt stmt = (Stmt) stmtIt.next();
                if ((stmt instanceof ReturnStmt) || (stmt instanceof ReturnVoidStmt)) {
                    InvokeExpr reportExpr = Jimple.v().newStaticInvokeExpr(reportCounter.makeRef());
                    Stmt reportStmt = Jimple.v().newInvokeStmt(reportExpr);
                    units.insertBefore(reportStmt, stmt);
                }
            }
        }
    }

    private void insertDisplayIfClassInit(SootMethod method, Chain units) {
        String signature = method.getSubSignature();
        boolean isClassInit = signature.equals("void <clinit>()");
        if (isClassInit) {
            Iterator stmtIt = units.snapshotIterator();
            Stmt stmt = null;
            while (stmtIt.hasNext()) {
                stmt = (Stmt) stmtIt.next();
            }
            InvokeExpr displayExpr = Jimple.v().newStaticInvokeExpr(displayCounter.makeRef());
            Stmt displayStmt = Jimple.v().newInvokeStmt(displayExpr);
            units.insertBefore(displayStmt, stmt);
        }
    }

    protected void internalTransform(Body body, String phase, Map options) {
        SootMethod method = body.getMethod();
        Chain units = body.getUnits();
        Iterator stmtIt = units.snapshotIterator();

        System.out.println(body.getMethod().getSignature());
        while (stmtIt.hasNext()) {
            Stmt stmt = (Stmt) stmtIt.next();
            if (!stmt.containsInvokeExpr()) {
                continue;
            }
            InvokeExpr expr = (InvokeExpr) stmt.getInvokeExpr();
            if (!(expr instanceof StaticInvokeExpr)) {
                continue;
            }

            InvokeExpr incExpr = Jimple.v().newStaticInvokeExpr(increaseCounter.makeRef(), IntConstant.v(1));
            Stmt incStmt = Jimple.v().newInvokeStmt(incExpr);
            units.insertBefore(incStmt, stmt);
        }

        insertReportIfMain(method, units);
        insertDisplayIfClassInit(method, units);
    }
}
